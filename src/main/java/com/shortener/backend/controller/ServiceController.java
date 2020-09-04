package com.shortener.backend.controller;

import com.shortener.backend.dto.*;
import com.shortener.backend.service.URLService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@RestController
@RequestMapping("/v1/shortURLs")
@Validated
public class ServiceController {
    private URLService service;

    @Autowired
    public ServiceController(URLService service) {
        this.service = service;
    }

    @ApiOperation(value = "generate a shorted link.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK", response = String.class),
            @ApiResponse(code = 409, message = "CONFLICT", response = String.class),
            @ApiResponse(code = 500, message = "internal error"),
            @ApiResponse(code = 400, message = "bad request")})
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> registerLink(@Valid @RequestBody RegisterLinkRequestDto newLink) {
        RegisterLinkResponseDto responseDto = service.registerLink(newLink);
        return new ResponseEntity<>(responseDto.getShortURL(), responseDto.isExistBefore() ? HttpStatus.ALREADY_REPORTED : HttpStatus.CREATED );
    }

    @ApiOperation(value = "Get main URL.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 500, message = "internal error"),
            @ApiResponse(code = 400, message = "bad request")})
    @GetMapping()
    public ResponseEntity<String> getLongURL(@Pattern(regexp = "[a-zA-Z0-9]{5}", message = "only 5 character is accepted") @RequestParam String identifier) {
        String longURL = service.returnLongURL(identifier);
        return new ResponseEntity<>(longURL, longURL != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
