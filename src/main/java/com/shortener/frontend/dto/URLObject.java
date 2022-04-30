package com.shortener.frontend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author M.dehghan
 * @since 2020-09-03
 */
@Builder
@ToString
@Getter
public class URLObject {
    private String longURL;
    private String shortUrl;
}
