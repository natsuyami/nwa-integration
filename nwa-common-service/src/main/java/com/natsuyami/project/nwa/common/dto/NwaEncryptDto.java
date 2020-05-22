package com.natsuyami.project.nwa.common.dto;

import java.io.Serializable;

public class NwaEncryptDto implements Serializable {

  private static final long serialVersionUID = -7678455426513759826L;

  private String password;
  private String code;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("{ \"password\":")
        .append(this.password)
        .append(", \"code\":")
        .append(this.code);

    return str.toString();
  }
}
