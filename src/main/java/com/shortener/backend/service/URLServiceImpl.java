package com.shortener.backend.service;

import com.shortener.backend.business.ShortURLConstruction;
import com.shortener.backend.dto.*;
import com.shortener.backend.entity.URLEntity;
import com.shortener.backend.repository.URLRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@Service
@Transactional
public class URLServiceImpl implements URLService {
    private static final Logger logger = getLogger(URLServiceImpl.class);
    private URLRepository urlRepository;
    private ShortURLConstruction shortURLConstruction;

    @Autowired
    public void setUrlRepository(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Autowired
    public void setShortURLConstruction(ShortURLConstruction shortURLConstruction) {
        this.shortURLConstruction = shortURLConstruction;
    }

    @Override
    public RegisterLinkResponseDto registerLink(RegisterLinkRequestDto newLink) {
        String generatedShortURL;
        boolean isExistBefore = false;
        Optional<String> shortURL = checkLongURLAndReturnShortURL(newLink);
        if (shortURL.isPresent()) {
            isExistBefore = true;
            logger.debug("the link was registered before. link= {}", newLink.getLongURL());
            generatedShortURL = shortURL.get();
        } else {
            URLEntity entity = generateRandomURLAndCheckUniqueness();
            entity.setLongURL(newLink.getLongURL());
            entity.setCounter(0);
            generatedShortURL = entity.getShortUrl();
            urlRepository.save(entity);
        }
        return RegisterLinkResponseDto.builder()
                .shortURL(generatedShortURL)
                .isExistBefore(isExistBefore)
                .build();
    }

    private Optional<String> checkLongURLAndReturnShortURL(RegisterLinkRequestDto newLink) {
        String shortURL = null;
        URLEntity entity = URLEntity.builder()
                .longURL(newLink.getLongURL())
                .build();
        Example<URLEntity> example = Example.of(entity);
        Optional<URLEntity> queryResult = urlRepository.findOne(example);
        if (queryResult.isPresent())
            shortURL = queryResult.get().getShortUrl();
        return Optional.ofNullable(shortURL);
    }

    private URLEntity generateRandomURLAndCheckUniqueness() {
        String shortRandomURL = shortURLConstruction.generate();
        URLEntity entity = URLEntity.builder()
                .shortUrl(shortRandomURL)
                .build();
        Example<URLEntity> example = Example.of(entity);
        Optional<URLEntity> queryResult = urlRepository.findOne(example);
        if (queryResult.isPresent())
            generateRandomURLAndCheckUniqueness();
        return entity;
    }

    @Override
    public String returnLongURL(String shortLink) {
        String longURL = null;
        URLEntity searchEntity = URLEntity.builder()
                .shortUrl(shortLink)
                .build();
        Example<URLEntity> example = Example.of(searchEntity);
        Optional<URLEntity> queryResult = urlRepository.findOne(example);
        if (queryResult.isPresent()) {
            URLEntity findEntity = queryResult.get();
            longURL = findEntity.getLongURL();
            logger.debug("Link counter increment process. shortLink({}) was visited {} times before the last.",
                    shortLink, findEntity.getCounter());
            findEntity.setCounter(queryResult.get().getCounter() + 1);
            urlRepository.save(findEntity);
        }
        return longURL;
    }
}
