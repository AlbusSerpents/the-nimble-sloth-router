package com.nimble.sloth.router.func.liveliness;

import com.nimble.sloth.router.func.liveliness.Liveliness.ApplicationStatusOption;
import com.nimble.sloth.router.func.status.StatusRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.nimble.sloth.router.func.liveliness.Liveliness.ApplicationStatusOption.*;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Controller
public class LivelinessChecker {

    private static final Log LOG = LogFactory.getLog(LivelinessChecker.class);

    private static final String STATUS_SUFFIX = "/status";
    private static final long MILLISECONDS_IN_MINUTE = 1000 * 60;
    private static final long MILLISECONDS_IN_TEN_MINUTES = 10 * MILLISECONDS_IN_MINUTE;

    private final StatusRepository repository;

    public LivelinessChecker(final StatusRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = MILLISECONDS_IN_TEN_MINUTES)
    public void checkStatuses() {
        final String startSchedulingMessage = String.format("Starting log at %s", now().toString());
        LOG.info(startSchedulingMessage);

        final List<Liveliness> newStatuses = loadNewStatuses();
        repository.updateStatuses(newStatuses);

        final String endSchedulingMessage = String.format("Starting log at %s", now().toString());
        LOG.info(endSchedulingMessage);
    }

    private List<Liveliness> loadNewStatuses() {
        return repository
                .statusReport()
                .parallelStream()
                .map(this::toStatusRequest)
                .map(this::requestStatus)
                .collect(toList());
    }

    private StatusRequest toStatusRequest(final Liveliness status) {
        final String applicationName = status.getApplicationName();
        final String applicationAddress = status.getApplicationAddress();
        return new StatusRequest(applicationName, applicationAddress);
    }

    private Liveliness requestStatus(final StatusRequest request) {
        final String appId = request.getAppId();
        final String startMessage = String.format("Starting check for %s", appId);
        LOG.info(startMessage);

        final String fullUrl = makeUrl(request);
        final ApplicationStatusOption applicationStatus = callServer(fullUrl);
        return new Liveliness(appId, request.getUrl(), applicationStatus);
    }

    private String makeUrl(final StatusRequest request) {
        return request.getUrl() + STATUS_SUFFIX;
    }

    private ApplicationStatusOption callServer(final String fullUrl) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            final ResponseEntity<Object> result = restTemplate.getForEntity(fullUrl, Object.class);
            return result.getStatusCode().is2xxSuccessful() ? OK : DOWN;
        } catch (Exception e) {
            LOG.error(e);
            return NOT_RESPONDING;
        }
    }

    @Data
    @AllArgsConstructor
    private static class StatusRequest {
        private final String appId;
        private final String url;
    }
}
