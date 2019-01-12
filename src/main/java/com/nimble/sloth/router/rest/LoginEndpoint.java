package com.nimble.sloth.router.rest;

import com.nimble.sloth.router.auth.AuthResponse;
import com.nimble.sloth.router.auth.AuthService;
import com.nimble.sloth.router.auth.LoginRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class LoginEndpoint {
    private final AuthService service;

    public LoginEndpoint(final AuthService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/login", method = POST)
    public AuthResponse login(
            final HttpSession session,
            final @RequestBody @Valid LoginRequest request) {
        return service.authenticate(request, session.getId());
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "global/logout", method = DELETE)
    public void logout(final HttpSession session) {
        session.invalidate();
    }
}
