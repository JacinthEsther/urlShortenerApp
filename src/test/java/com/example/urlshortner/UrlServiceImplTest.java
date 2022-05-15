package com.example.urlshortner;

import com.example.urlshortner.dtos.requests.UrlDto;
import com.example.urlshortner.dtos.responses.UrlResponse;
import com.example.urlshortner.exception.UrlException;
import com.example.urlshortner.models.Url;
import com.example.urlshortner.repositories.UrlRepository;
import com.example.urlshortner.services.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UrlServiceImplTest {

    @Autowired
    private UrlService service;


    @Test
    void testToSetAndGetUrl(){
        Url url = new Url();
        url.setOriginalUrl("www.facebook.com");
        assertEquals("www.facebook.com", url.getOriginalUrl());
    }

    @Test
    void shortLinkCanBeGenerated() throws UrlException {
      UrlDto request = new UrlDto();
      request.setUrl("https://www.facebook.com");
      UrlResponse response = service.generateShortLink(request);

        assertNotNull(response.getShortenedUrl());
    }

    @Test
    void TestToGetOriginalUrl() throws UrlException {
        UrlDto request = new UrlDto();
        request.setUrl("https://www.facebook.com");
        UrlResponse response = service.generateShortLink(request);

        assertEquals("https://www.facebook.com" , response.getOriginalUrl());
    }
}
