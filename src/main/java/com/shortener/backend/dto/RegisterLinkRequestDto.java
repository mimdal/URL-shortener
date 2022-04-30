package com.shortener.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@Getter
@Setter
@ToString
public class RegisterLinkRequestDto {
    @NotEmpty(message = "longURL: null or empty not approved.")
    @Pattern(message = "Only web link address is acceptable. ", regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
    private String longURL;
}
