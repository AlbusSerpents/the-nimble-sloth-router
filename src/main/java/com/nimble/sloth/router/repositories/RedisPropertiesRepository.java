package com.nimble.sloth.router.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimble.sloth.router.func.exceptions.BadFormat;
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
    private final ObjectMapper mapper = new ObjectMapper();

    public RedisPropertiesRepository(final BaseRedisRepository base) {
        this.base = base;
    }

    @Override
    public Set<AppProperty> getProperties(final String appId) {
        final RedisKey key = makeKey(appId);
        return base
                .get(key)
                .map(this::deserialize)
                .orElse(emptySet());
    }

    @Override
    public void setProperties(final String appId, final Set<AppProperty> properties) {
        final RedisKey key = makeKey(appId);
        final String value = serialize(properties);
        base.put(key, value);
    }

    private RedisKey makeKey(final String appId) {
        return () -> appId + ":" + PROPERTIES_KEY_SUFFIX;
    }

    private Set<AppProperty> deserialize(final String json) {
        try {
            return mapper.readValue(json, PropertiesWrapper.class).properties;
        } catch (Exception e) {
            throw new BadFormat(json);
        }
    }

    private String serialize(final Set<AppProperty> properties) {
        try {
            final PropertiesWrapper wrapper = new PropertiesWrapper(properties);
            return mapper.writeValueAsString(wrapper);
        } catch (Exception e) {
            throw new BadFormat(e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PropertiesWrapper {
        @NotNull
        private Set<AppProperty> properties;
    }
}
