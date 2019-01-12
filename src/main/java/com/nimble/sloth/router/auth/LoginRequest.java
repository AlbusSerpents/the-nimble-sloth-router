package com.nimble.sloth.router.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String appId;
    private String token;
}
