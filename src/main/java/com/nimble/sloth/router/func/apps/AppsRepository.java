package com.nimble.sloth.router.func.apps;

import java.util.Optional;

public interface AppsRepository {
    void createApp(final String appId, final App app);

    Optional<String> getTokenForApp(final String appId);

    Optional<App> getAppDetails(final String appId);
}
