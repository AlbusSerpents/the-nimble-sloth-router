package com.nimble.sloth.router.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimble.sloth.router.func.apps.App;
import com.nimble.sloth.router.func.apps.AppsRepository;
import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisAppsRepository implements AppsRepository {

    private static final String TOKEN_EXTENSION = "token";

    private final BaseRedisRepository base;
    private final ObjectMapper mapper = new ObjectMapper();

    public RedisAppsRepository(final BaseRedisRepository base) {
        this.base = base;
    }


    @Override
    public void createApp(final String appId, final App app) {
        final RedisKey key = makeKey(appId);
        base.put(key, serialize(app));
    }

    @Override
    public Optional<String> getTokenForApp(final String appId) {
        final RedisKey key = makeKey(appId);
        return base
                .get(key)
                .map(this::deserialize)
                .map(App::getToken);
    }

    private RedisKey makeKey(final String key) {
        return () -> key + ":" + TOKEN_EXTENSION;
    }

    private App deserialize(final String json) {
        try {
            return mapper.readValue(json, App.class);
        } catch (Exception e) {
            throw new BadFormat(json);
        }
    }

    private String serialize(final App app) {
        try {
            return mapper.writeValueAsString(app);
        } catch (Exception e) {
            throw new BadFormat(e);
        }
    }
}
