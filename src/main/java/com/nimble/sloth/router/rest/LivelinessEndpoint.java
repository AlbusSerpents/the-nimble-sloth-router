package com.nimble.sloth.router.rest;

import com.nimble.sloth.router.func.liveliness.AppLiveliness;
import com.nimble.sloth.router.func.liveliness.LivelinessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/liveliness")
public class LivelinessEndpoint {

    private final LivelinessService service;

    public LivelinessEndpoint(final LivelinessService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public List<AppLiveliness> livelinessReport() {
        return service.livelinessReport();
    }
}
