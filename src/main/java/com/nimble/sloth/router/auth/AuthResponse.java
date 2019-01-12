package com.nimble.sloth.router.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private final String sessionId;
    private final String appId;
    private final String token;
}
