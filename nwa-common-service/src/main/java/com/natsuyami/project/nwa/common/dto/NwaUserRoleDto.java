package com.natsuyami.project.nwa.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

public class NwaUserRoleDto implements Serializable {

  private static final long serialVersionUID = 4438008209458116067L;

  @JsonInclude(Include.NON_NULL)
  private String id;

  @JsonInclude(Include.NON_NULL)
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
