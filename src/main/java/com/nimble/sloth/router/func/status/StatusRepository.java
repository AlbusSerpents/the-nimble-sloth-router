package com.nimble.sloth.router.func.status;

public interface StatusRepository {

    String getApplicationName();

    String getApplicationAddress();

    void setApplicationStatus(final ApplicationStatus status);
}
