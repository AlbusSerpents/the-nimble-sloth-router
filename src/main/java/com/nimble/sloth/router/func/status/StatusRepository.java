package com.nimble.sloth.router.func.status;

import com.nimble.sloth.router.func.liveliness.Liveliness;

import java.util.List;

public interface StatusRepository {

    String getApplicationName();

    String getApplicationAddress();

    void setApplicationStatus(final ApplicationStatus status);

    List<Liveliness> statusReport();

    void updateStatuses(final List<Liveliness> statuses);
}
