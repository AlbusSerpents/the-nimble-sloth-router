package com.nimble.sloth.router.rest;

import com.nimble.sloth.router.func.properties.AppProperty;
import com.nimble.sloth.router.func.properties.PropertiesService;
import com.nimble.sloth.router.func.properties.UpdateAppPropertiesRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/properties")
public class PropertiesEndpoint {
    private final PropertiesService service;

    public PropertiesEndpoint(final PropertiesService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{appId}", method = GET)
    public Set<AppProperty> getProperties(final @PathVariable("appId") String appId) {
        return service.getProperties(appId);
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{appId}", method = PUT)
    public void updateProperties(
            final @PathVariable("appId") String appId,
            final @RequestBody @Valid UpdateAppPropertiesRequest request) {
        service.updateProperties(appId, request);
    }
}
