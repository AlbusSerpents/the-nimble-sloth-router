package com.nimble.sloth.router.repositories;

import com.nimble.sloth.router.func.properties.AppProperty;
import com.nimble.sloth.router.func.properties.PropertiesRepository;
import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Set;

import static java.util.Collections.emptySet;

@Repository
public class RedisPropertiesRepository implements PropertiesRepository {

    private static final String PROPERTIES_KEY_SUFFIX = "properties";

    private final BaseRedisRepository base;

    public RedisPropertiesRepository(final BaseRedisRepository base) {
        this.base = base;
    }

    @Override
    public Set<AppProperty> getProperties(final String appId) {
        final RedisKey key = makeKey(appId);
        return base
                .get(key, PropertiesWrapper.class)
                .map(PropertiesWrapper::getProperties)
                .orElse(emptySet());
    }

    @Override
    public void setProperties(final String appId, final Set<AppProperty> properties) {
        final RedisKey key = makeKey(appId);
        base.put(key, new PropertiesWrapper(properties));
    }

    private RedisKey makeKey(final String appId) {
        return () -> appId + ":" + PROPERTIES_KEY_SUFFIX;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PropertiesWrapper {
        @NotNull
        private Set<AppProperty> properties;
    }
}
