package com.natsuyami.project.nwa.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class NwaUserKeycloakDto implements Serializable {

  private static final long serialVersionUID = 4166698063371331180L;

  private String id;

  private String username;

  private String firstName;

  private String lastName;

  private Boolean enabled;

  private Boolean emailVerified;

  private String email;

  private ArrayList<NwaCredentialDto> credentials;

  private Map<String, ArrayList> attributes;

  @JsonInclude(Include.NON_NULL)
  private Timestamp createdTimestamp;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ArrayList<NwaCredentialDto> getCredentials() {
    return credentials;
  }

  public void setCredentials(
      ArrayList<NwaCredentialDto> credentials) {
    this.credentials = credentials;
  }

  public Map<String, ArrayList> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, ArrayList> attributes) {
    this.attributes = attributes;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public Timestamp getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Timestamp createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }
}
