package com.nimble.sloth.router.rest;

import com.nimble.sloth.router.auth.AuthResponse;
import com.nimble.sloth.router.func.apps.AppService;
import com.nimble.sloth.router.func.apps.NewAppRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/new-app")
public class RegistrationEndpoint {

    private final AppService service;

    public RegistrationEndpoint(final AppService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "", method = POST)
    public AuthResponse register(
            final HttpSession session,
            final @RequestBody @Valid NewAppRequest request) {
        return service.createApp(request, session.getId());
    }
}
