package com.mms.model;
public enum ERole {
  ROLE_USER(1), ROLE_MODERATOR(2), ROLE_ADMIN(3);
  private final int id;

  ERole(int id) {
    this.id = id;
  }

  public int id() {
    return id;
  }

  public static ERole byId(int code) {
    for (ERole type : ERole.values()) {
      if (code == type.id()) return type;
    }
    throw new IllegalArgumentException("invalid id");
  }

}