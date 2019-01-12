package com.nimble.sloth.router.rest;

import com.nimble.sloth.router.func.apps.App;
import com.nimble.sloth.router.func.apps.AppService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/apps")
public class AppsEndpoint {

    private final AppService service;

    public AppsEndpoint(final AppService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{appId}", method = GET)
    public App getApp(final @PathVariable("appId") String appId) {
        return service.getById(appId);
    }
}
