package com.nimble.sloth.router.auth;

public enum Role {
    AUTHENTICATED;

    public String asUserRole() {
        return name();
    }

    public String asRoleAuthorityName() {
        return "ROLE_" + name();
    }
}
