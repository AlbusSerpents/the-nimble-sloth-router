package com.nimble.sloth.router.repositories;

import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
class NoResult extends RuntimeException {

    private static final String MESSAGE = "No result for %s";

    NoResult(final RedisKey key) {
        super(toMessage(key));
    }

    private static String toMessage(final RedisKey key) {
        return String.format(MESSAGE, key.getKey());
    }
}
