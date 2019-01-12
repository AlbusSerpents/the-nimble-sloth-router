package com.nimble.sloth.router.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableSpringHttpSession
public class SessionConfig {
    private static final int SESSION_LENGTH = 60 * 60;

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        final Map<String, Session> sessions = new HashMap<>();
        final MapSessionRepository repository = new MapSessionRepository(sessions);
        repository.setDefaultMaxInactiveInterval(SESSION_LENGTH);
        return repository;
    }
}
