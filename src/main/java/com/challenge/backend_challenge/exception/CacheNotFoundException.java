package com.challenge.backend_challenge.exception;

import com.challenge.backend_challenge.enums.ErrorMessage;

public class CacheNotFoundException extends CacheException {
    public CacheNotFoundException(Throwable cause) {
        super(ErrorMessage.NO_CACHED_VALUE, cause);
    }

    public CacheNotFoundException() {
        super(ErrorMessage.NO_CACHED_VALUE);
    }
}
