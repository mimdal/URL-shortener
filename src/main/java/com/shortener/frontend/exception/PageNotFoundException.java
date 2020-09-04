package com.shortener.frontend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author M.dehghan
 * @since 2020-09-04
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Page not found")
public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(String message) {
        super(message);
    }
}
