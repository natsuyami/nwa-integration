package com.natsuyami.project.nwa.common.http;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
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

  public <S> S get(String uri, Class<S> responseClass) {

    return webClient.get()
        .uri(uri)
        .retrieve()
        .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
        .block();
  }

  public <S> List<S> getList(String uri, Class<S> responseClass) {

    return webClient.get()
        .uri(uri)
        .retrieve()
        .bodyToFlux(responseClass) // responsible for the type of object that the request retrieve
        .collectList() // when expected retrieve is a list
        .block();
  }

  public <S,T> S post(String uri, Class<T> requestParam, Class<S> responseClass) {

    return webClient.post()
        .uri(uri)
        .bodyValue(requestParam) // body parameter for the request (type is generic)
        .retrieve()
        .bodyToMono(responseClass) // responsible for the type of object that the request retrieve
        .block();
  }
}
