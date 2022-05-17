package com.example.urlshortner;

import com.example.urlshortner.dtos.requests.UrlDto;
import com.example.urlshortner.dtos.responses.UrlResponse;
import com.example.urlshortner.exception.UrlException;
import com.example.urlshortner.models.Url;
import com.example.urlshortner.repositories.UrlRepository;
import com.example.urlshortner.services.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UrlServiceImplTest {

    @Autowired
    private UrlService service;

    UrlDto request;
    @BeforeEach
    void setUp(){
        request  = new UrlDto();
        request.setUrl("https://www.facebook.com");
    }

    @Test
    void testToSetAndGetUrl(){
        Url url = new Url();
        url.setOriginalUrl("www.facebook.com");
        assertEquals("www.facebook.com", url.getOriginalUrl());
    }

//    @Test
//    void shortLinkCanBeGeneratedTest() throws UrlException {
//
//      UrlResponse response = service.generateShortLink(request);
//
//       service.getEncodedUrl("https://www.facebook.com");
//
//      assertNotNull(response.getShortenedUrl());
////      assertEquals(response.getShortenedUrl(), getShortUrl);
//
//    }

    @Test
    void testToGetOriginalUrl() throws UrlException {

        UrlResponse response = service.generateShortLink(request);

        assertEquals("https://www.facebook.com" , response.getOriginalUrl());
    }

   @Test
   void invalidUrlThrowsExceptionTest(){
       UrlDto request = new UrlDto();
       request.setUrl("htt://www.facebook.com");
      assertThrows(UrlException.class,()->service.generateShortLink(request) );
   }

   @Test
    void testThatEmptyUrlCannotBeInputted(){
       UrlDto request = new UrlDto();
       request.setUrl(" ");
       assertThrows(UrlException.class,()->service.generateShortLink(request) );
   }

   @Test
    void testThatUpdateUrl() throws UrlException {

       UrlResponse response = service.generateShortLink(request);

       String shortUrl = service.updateShortLink(response.getShortenedUrl(),"friend");
       String url = service.getEncodedUrl("https://www.facebook.com");
       assertEquals(shortUrl, url);
   }

    @AfterEach
    void tearDown() {
        Url url = new Url();
        url.setOriginalUrl("https://www.facebook.com");
        service.delete(url);
    }
}
