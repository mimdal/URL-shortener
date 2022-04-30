package com.shortener.backend.service;

import com.shortener.backend.dto.RegisterLinkRequestDto;
import com.shortener.backend.dto.RegisterLinkResponseDto;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
public interface URLService {
    RegisterLinkResponseDto registerLink(RegisterLinkRequestDto newLink);
    String returnLongURL(String shortLink);
}
