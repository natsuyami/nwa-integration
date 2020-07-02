package com.natsuyami.project.nwa.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class NwaTokenDto implements Serializable {

  private static final long serialVersionUID = 3429789906171015679L;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("expires_in")
  private String expiresIn;

  @JsonProperty("refresh_expires_in")
  private String refreshExpiresIn;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(String expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getRefreshExpiresIn() {
    return refreshExpiresIn;
  }

  public void setRefreshExpiresIn(String refreshExpiresIn) {
    this.refreshExpiresIn = refreshExpiresIn;
  }
}
