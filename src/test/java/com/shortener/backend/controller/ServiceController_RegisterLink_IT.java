package com.shortener.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shortener.backend.dto.RegisterLinkRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application-test.properties"})
class ServiceController_RegisterLink_IT {

    private static final String SERVICE_ENDPOINT = "/v1/shortURLs/";

    private static final String CONTENT_TYPE = "application/json";

    private static final String BASE_LONG_URL = "http://sample.com/";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    @DisplayName("Given: web server starts, When: a new URL is passed to web service, Then: rest status is 201(Created)")
    void registerLink_newURLIsPassed() throws Exception {
        RegisterLinkRequestDto request = new RegisterLinkRequestDto();
        request.setLongURL(BASE_LONG_URL + "1");
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_ENDPOINT).contentType(CONTENT_TYPE).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Given: web server starts, When: a repetitive URL is passed, Then: rest status is 208(ALREADY_REPORTED)")
    void registerLink_repetitiveURLIsPassed() throws Exception {
        RegisterLinkRequestDto request = new RegisterLinkRequestDto();
        request.setLongURL(BASE_LONG_URL + "2");
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_ENDPOINT).contentType(CONTENT_TYPE).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_ENDPOINT).contentType(CONTENT_TYPE).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isAlreadyReported());
    }

    @Test
    @DisplayName("Given: web server starts, When: a bad URL format is passed, Then: rest status is 400(BAD_REQUEST)")
    void registerLink_badURLIsPassed() throws Exception {
        RegisterLinkRequestDto request = new RegisterLinkRequestDto();
        request.setLongURL("bad url format that doesn't start by http or www");
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_ENDPOINT).contentType(CONTENT_TYPE).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
