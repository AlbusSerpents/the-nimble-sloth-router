package com.nimble.sloth.router.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthProvider provider;

    public AuthService(final AuthProvider provider) {
        this.provider = provider;
    }

    public AuthResponse authenticate(
            final String appId,
            final String token,
            final String sessionId) {
        final Authentication request = new UsernamePasswordAuthenticationToken(appId, token);
        final Authentication authentication = provider.authenticate(request);

        final SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return new AuthResponse(sessionId, appId, token);
    }
}
