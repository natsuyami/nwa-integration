package com.natsuyami.project.nwa.common.dto;

import java.io.Serializable;

public class NwaTokenDto implements Serializable {

  private static final long serialVersionUID = 3429789906171015679L;
  private String access_token;

  private String refresh_token;

  private String expires_in;

  private String refresh_expires_in;

  public String getAccessToken() {
    return access_token;
  }

  public void setAccessToken(String access_token) {
    this.access_token = access_token;
  }

  public String getRefreshToken() {
    return refresh_token;
  }

  public void setRefreshToken(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public String getExpiresIn() {
    return expires_in;
  }

  public void setExpiresIn(String expires_in) {
    this.expires_in = expires_in;
  }

  public String getRefreshExpiresIn() {
    return refresh_expires_in;
  }

  public void setRefreshExpiresIn(String refresh_expires_in) {
    this.refresh_expires_in = refresh_expires_in;
  }
}
