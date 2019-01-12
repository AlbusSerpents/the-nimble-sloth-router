package com.nimble.sloth.router.rest;

import com.nimble.sloth.router.func.status.ApplicationStatus;
import com.nimble.sloth.router.func.status.StatusService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/status")
public class StatusEndpoint {

    private final StatusService service;

    public StatusEndpoint(final StatusService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public ApplicationStatus status() {
        return service.getStatus();
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "", method = POST)
    public ApplicationStatus loadData(final @RequestBody ApplicationStatus status) {
        return service.createStatus(status);
    }

}
