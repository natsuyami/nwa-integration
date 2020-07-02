package com.natsuyami.project.nwa.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

public class NwaCredentialDto implements Serializable {

  private static final long serialVersionUID = 5610250417500770739L;

  @JsonInclude(Include.NON_NULL)
  private String type;

  @JsonInclude(Include.NON_NULL)
  private String value;

  @JsonInclude(Include.NON_NULL)
  private Boolean temporary;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Boolean getTemporary() {
    return temporary;
  }

  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }
}
