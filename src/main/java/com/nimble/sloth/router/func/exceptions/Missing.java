package com.nimble.sloth.router.func.exceptions;

import com.nimble.sloth.router.repositories.BaseRedisRepository.RedisKey;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class Missing extends CustomException {
    private static final String MESSAGE_TEMPLATE = "Resource %s is missing";
    private static final String LOG_MESSAGE = "No result for %s";

    public Missing(final String identifier) {
        super(toMessage(MESSAGE_TEMPLATE, identifier));
    }

    public Missing(final RedisKey identifier) {
        super(toMessage(MESSAGE_TEMPLATE, identifier.getKey()), toMessage(LOG_MESSAGE, identifier.getKey()));
    }

    private static String toMessage(
            final String messageTemplate,
            final String identifier) {
        return String.format(messageTemplate, identifier);
    }
}
