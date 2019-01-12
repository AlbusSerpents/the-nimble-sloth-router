package com.nimble.sloth.router.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimble.sloth.router.func.exceptions.BadFormat;
import com.nimble.sloth.router.func.exceptions.Missing;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@SuppressWarnings("WeakerAccess")
@Repository
public class BaseRedisRepository {
    private final Jedis jedis;
    private final ObjectMapper mapper = new ObjectMapper();

    public BaseRedisRepository(final Jedis jedis) {
        this.jedis = jedis;
    }

    public interface RedisKey {
        String getKey();
    }

    public <T> Optional<T> get(
            final RedisKey redisKey,
            final Class<T> responseClass) {
        final String key = redisKey.getKey();
        final String result = jedis.get(key);
        return ofNullable(result)
                .map(value -> deserialize(value, responseClass));
    }

    public <T> T getRequired(
            final RedisKey redisKey,
            final Class<T> responseClass) {
        return get(redisKey, responseClass).orElseThrow(() -> new Missing(redisKey));
    }

    public void put(final RedisKey redisKey, final String value) {
        final String key = redisKey.getKey();
        jedis.set(key, value);
    }

    public <T> void put(final RedisKey redisKey, final T object) {
        final String key = redisKey.getKey();
        final String value = serialize(object);
        jedis.set(key, value);
    }


    private <T> T deserialize(final String json, final Class<T> resultClass) {
        try {
            return mapper.readValue(json, resultClass);
        } catch (Exception e) {
            throw new BadFormat(json);
        }
    }

    private <T> String serialize(final T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new BadFormat(e);
        }
    }

}
