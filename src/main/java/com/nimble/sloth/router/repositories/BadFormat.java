package com.nimble.sloth.router.repositories;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@ResponseStatus(BAD_GATEWAY)
class BadFormat extends RuntimeException {

    private static final String DESERIALIZE_MESSAGE = "Can't parse %s";
    private static final String SERIALIZE_MESSAGE = "Can't serialize object";

    BadFormat(final String json) {
        super(toMessage(json));
    }

    BadFormat(final Exception e) {
        super(SERIALIZE_MESSAGE, e);
    }

    private static String toMessage(final String json) {
        return String.format(DESERIALIZE_MESSAGE, json);
    }

}
