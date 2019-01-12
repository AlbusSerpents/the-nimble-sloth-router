package com.nimble.sloth.router.func.apps;

import com.nimble.sloth.router.auth.AuthResponse;
import com.nimble.sloth.router.auth.AuthService;
import com.nimble.sloth.router.func.exceptions.Missing;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private final AppsRepository repository;
    private final TokenGenerator generator;
    private final AuthService authService;

    public AppService(
            final AppsRepository repository,
            final TokenGenerator generator,
            final AuthService authService) {
        this.repository = repository;
        this.generator = generator;
        this.authService = authService;
    }

    public AuthResponse createApp(final NewAppRequest request, final String sessionId) {
        final String token = generator.makeToken();
        final String appId = request.getAppId();
        final App app = new App(appId, request.getUrl(), token);
        repository.createApp(appId, app);
        return authService.authenticate(appId, token, sessionId);
    }

    public App getById(final String appId) {
        return repository
                .getAppDetails(appId)
                .orElseThrow(() -> new Missing(appId));
    }
}
