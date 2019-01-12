package com.nimble.sloth.router.repositories;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class BaseRedisRepository {
    private final Jedis jedis;

    public BaseRedisRepository(final Jedis jedis) {
        this.jedis = jedis;
    }

    public interface RedisKey {
        String getKey();
    }

    public Optional<String> get(final RedisKey redisKey) {
        final String key = redisKey.getKey();
        final String result = jedis.get(key);
        return ofNullable(result);
    }

    public String getRequired(final RedisKey redisKey) {
        return get(redisKey).orElseThrow(() -> new NoResult(redisKey));
    }

    public Optional<String> put(final RedisKey redisKey, final String value) {
        final String key = redisKey.getKey();
        final String result = jedis.set(key, value);
        return ofNullable(result);
    }
}
