package com.shortener.backend.dto;

import lombok.*;

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
