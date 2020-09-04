package com.shortener.backend.exception;

/**
 * @author M.dehghan
 * @since 2020-09-03
 */
public class LinkShortenerRuntimeException extends RuntimeException {

    public LinkShortenerRuntimeException(String message) {
        super(message);
    }

    public LinkShortenerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
