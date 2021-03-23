package com.mms.model;
public enum ERole {
  ROLE_USER(1), ROLE_MODERATOR(2), ROLE_ADMIN(3);
  private final long id;

  ERole(long id) {
    this.id = id;
  }

  public long id() {
    return id;
  }

  public static ERole byId(long code) {
    for (ERole type : ERole.values()) {
      if (code == type.id()) return type;
    }
    throw new IllegalArgumentException("invalid id");
  }

}