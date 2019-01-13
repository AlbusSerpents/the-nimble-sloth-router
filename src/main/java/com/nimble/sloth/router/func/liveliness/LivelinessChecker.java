package com.nimble.sloth.router.func.liveliness;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Controller
public class LivelinessChecker {

    private static final Log LOG = LogFactory.getLog(LivelinessChecker.class);

    private static final long MILLISECONDS_IN_MINUTE = 1000 * 60;
    private static final long MILLISECONDS_IN_TEN_MINUTES = 10 * MILLISECONDS_IN_MINUTE;

    private final LivelinessService service;

    public LivelinessChecker(final LivelinessService service) {
        this.service = service;
    }

    @Scheduled(fixedDelay = MILLISECONDS_IN_TEN_MINUTES)
    public void checkStatuses() {
        final String startSchedulingMessage = String.format("Starting log at %s", now().toString());
        LOG.info(startSchedulingMessage);

        final List<AppLiveliness> newStatuses = loadNewStatuses();
        service.updateLivelinessStatus(newStatuses);

        final String endSchedulingMessage = String.format("Ending log at %s", now().toString());
        LOG.info(endSchedulingMessage);
    }

    private List<AppLiveliness> loadNewStatuses() {
        return service
                .livelinessReport()
                .parallelStream()
                .map(this::toStatusRequest)
                .map(service::checkLiveliness)
                .collect(toList());
    }

    private LivelinessRequest toStatusRequest(final AppLiveliness status) {
        final String applicationName = status.getApplicationName();
        final String applicationAddress = status.getApplicationAddress();
        return new LivelinessRequest(applicationName, applicationAddress);
    }
}
