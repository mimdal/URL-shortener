package com.shortener.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@Getter
@Setter
@ToString
@Builder
public class RegisterLinkResponseDto {
    private String shortURL;
    private boolean isExistBefore;
}
