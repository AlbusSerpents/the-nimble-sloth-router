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
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginEndpoint {
    private final AuthService service;

    public LoginEndpoint(final AuthService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "", method = POST)
    public AuthResponse login(
            final HttpSession session,
            final @RequestBody @Valid LoginRequest request) {
        return service.authenticate(request, session.getId());
    }
}
