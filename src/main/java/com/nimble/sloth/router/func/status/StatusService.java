package com.nimble.sloth.router.func.status;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository repository;

    public StatusService(final StatusRepository repository) {
        this.repository = repository;
    }

    public ApplicationStatus getStatus() {
        final String appName = repository.getApplicationName();
        final String appAddress = repository.getApplicationAddress();

        return new ApplicationStatus(appName, appAddress, "OK");
    }

    public ApplicationStatus createStatus(final ApplicationStatus status) {
        repository.setApplicationStatus(status);
        return getStatus();
    }
}
