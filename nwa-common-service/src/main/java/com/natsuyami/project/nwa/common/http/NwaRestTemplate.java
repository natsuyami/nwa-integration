package com.natsuyami.project.nwa.common.http;

import com.natsuyami.project.nwa.common.constant.NwaContentType;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NwaRestTemplate {

  private static WebClient webClient;

  public NwaRestTemplate() {
    this.webClient = WebClient
        .builder()
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  public String uriBuilder(String uri, MultiValueMap<String, String> requestParam) {
    UriComponentsBuilder query = UriComponentsBuilder.fromUriString(uri);
    query.queryParams(requestParam);

    return query.toUriString();
  }

  public <S> S get(String uri, NwaContentType contentType, String token, Class<S> responseClass) {
    setContentType(contentType);

    if (!StringUtils.isEmpty(token)) {
      return webClient.get()
          .uri(uri)
          .header("Authorization", "Bearer " + token)
          .retrieve()
          .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
          .block();
    } else {
      return webClient.get()
          .uri(uri)
          .retrieve()
          .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
          .block();
    }
  }

  public <S> List<S> getList(String uri, NwaContentType contentType, String token, Class<S> responseClass) {
    setContentType(contentType);

    if (!StringUtils.isEmpty(token)) {
      return webClient.get()
          .uri(uri)
          .header("Authorization", "Bearer " + token)
          .retrieve()
          .bodyToFlux(responseClass) // responsible for the type of object that the request retrieve
          .collectList() // when expected retrieve is a list
          .block();
    } else {
      return webClient.get()
          .uri(uri)
          .retrieve()
          .bodyToFlux(responseClass) // responsible for the type of object that the request retrieve
          .collectList() // when expected retrieve is a list
          .block();
    }
  }

//  public <S,T> S post(String uri, NwaContentType contentType, Class<S> responseClass) {
//    setContentType(contentType);
//
//    return webClient.post()
//        .uri(uri)
//        .retrieve()
//        .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
//        .block();
//  }

  public <S,T> S post(String uri, T requestParam, NwaContentType contentType, String token, Class<S> responseClass) {
    setContentType(contentType);

    if (!StringUtils.isEmpty(token)) {
      return webClient.post()
          .uri(uri)
          .header("Authorization", "Bearer " + token)
          .bodyValue(requestParam) // body parameter for the request (type is generic)
          .retrieve()
          .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
          .block();
    } else {
      return webClient.post()
          .uri(uri)
          .bodyValue(requestParam) // body parameter for the request (type is generic)
          .retrieve()
          .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
          .block();
    }
  }

  public <S> S post(String uri, BodyInserter requestParam, NwaContentType contentType, String token, Class<S> responseClass) {
    setContentType(contentType);

    if (!StringUtils.isEmpty(token)) {
      return webClient.post()
          .uri(uri)
          .header("Authorization", "Bearer " + token)
          .body(requestParam) // body parameter for the request (type is generic)
          .retrieve()
          .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
          .block();
    } else {
      return webClient.post()
          .uri(uri)
          .body(requestParam) // body parameter for the request (type is generic)
          .retrieve()
          .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
          .block();
    }
  }

  /**
   * Data to fill-up in the body to request for token
   * @param clientId
   * @param clientSecret
   * @param username
   * @param password
   * @return BodyInserters
   */
  public BodyInserters.FormInserter createToken(String clientId, String clientSecret, String username, String password) {
    return BodyInserters
        .fromFormData("client_id", clientId)
        .with("client_secret", clientSecret)
        .with("username", username)
        .with("password", password)
        .with("grant_type", "password");
  }

  /**
  * set content type of the header for the request
  */
  private void setContentType(NwaContentType contentType) {

    switch (contentType) {
      case URL_ENCODED:
        this.webClient = WebClient
            .builder()
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();
        break;
      case TEXT_PLAIN:
        this.webClient = WebClient
            .builder()
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
            .build();
        break;
      default:
        this.webClient = WebClient
            .builder()
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        break;
    }
  }
}
