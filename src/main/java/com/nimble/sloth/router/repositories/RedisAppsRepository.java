package com.nimble.sloth.router.repositories;

import com.nimble.sloth.router.func.apps.App;
import com.nimble.sloth.router.func.apps.AppRecorderRepository;
import com.nimble.sloth.router.func.apps.AppsRepository;
import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisAppsRepository implements AppsRepository {

    private static final String TOKEN_EXTENSION = "token";

    private final BaseRedisRepository base;
    private final AppRecorderRepository recorderRepository;

    public RedisAppsRepository(
            final BaseRedisRepository base,
            final AppRecorderRepository recorderRepository) {
        this.base = base;
        this.recorderRepository = recorderRepository;
    }

    @Override
    public void createApp(final String appId, final App app) {
        final RedisKey key = makeKey(appId);
        base.put(key, app);
        recorderRepository.recordApp(appId, app.getUrl());
    }

    @Override
    public Optional<String> getTokenForApp(final String appId) {
        return getAppDetails(appId).map(App::getToken);
    }

    @Override
    public Optional<App> getAppDetails(final String appId) {
        final RedisKey key = makeKey(appId);
        return base.get(key, App.class);
    }

    private RedisKey makeKey(final String key) {
        return () -> key + ":" + TOKEN_EXTENSION;
    }
}
