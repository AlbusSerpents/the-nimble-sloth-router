package com.nimble.sloth.router.auth;

import com.nimble.sloth.router.func.apps.AppsRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nimble.sloth.router.auth.Role.AUTHENTICATED;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Component
public class AuthProvider implements AuthenticationProvider {

    private static final List<Role> ROLES = singletonList(AUTHENTICATED);

    private final AppsRepository repository;

    public AuthProvider(final AppsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String appId = authentication.getName();
        final String appToken = (String) authentication.getCredentials();

        return repository
                .getTokenForApp(appId)
                .filter(appToken::equals)
                .map(token -> new UsernamePasswordAuthenticationToken(appId, token, toAuthorities()))
                .orElseThrow(() -> badCredentials(appId));
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private RuntimeException badCredentials(final String appId) {
        final String message = String.format("App id: %s is unknown", appId);
        return new BadCredentialsException(message);
    }

    private List<? extends GrantedAuthority> toAuthorities() {
        return ROLES
                .stream()
                .map(Role::asRoleAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
