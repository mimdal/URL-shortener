package com.shortener.backend.controller;

import com.shortener.backend.entity.URLEntity;
import com.shortener.backend.repository.URLRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class ServiceController_GetLongURL_IT {

    private static final String SERVICE_ENDPOINT = "/v1/shortURLs/?identifier=";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private URLRepository urlRepository;

    @Test
    @DisplayName("Given: web server starts, When: short-URL is persisted before, Then: long-URL return and link counter increments one value.")
    void getLongURL_shortURLWasSavedBefore() throws Exception {
        String shortURl = "a2c4e";
        String longURL = "http://longURL.com";
        Integer counter = 10;
        URLEntity urlEntity = URLEntity.builder()
                .shortUrl(shortURl)
                .longURL(longURL)
                .counter(counter)
                .build();
        urlRepository.save(urlEntity);

        mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_ENDPOINT + shortURl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(longURL));

        urlEntity = URLEntity.builder()
                .shortUrl(shortURl)
                .build();
        Example<URLEntity> example = Example.of(urlEntity);
        URLEntity urlEntityFromDatabase = urlRepository.findOne(example).get();

        Assertions.assertThat(urlEntityFromDatabase.getCounter()).isEqualTo(counter + 1);

    }

    @Test
    @DisplayName("Given: web server starts, When: short-URL is not persisted before, Then: server return NOT_FOUND.")
    void getLongURL_shortURLWasNotSavedBefore() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_ENDPOINT + "Abcde"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
