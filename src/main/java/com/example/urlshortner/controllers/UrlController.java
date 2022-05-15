package com.example.urlshortner.controllers;


import com.example.urlshortner.dtos.requests.UrlDto;
import com.example.urlshortner.exception.UrlException;
import com.example.urlshortner.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/url")
public class UrlController {


    @Autowired
    UrlService urlService;

    @PostMapping("/getShortLink")
    public ResponseEntity<?> getShortLinkOfUrl(@RequestBody UrlDto request){
        try{
            return new ResponseEntity<>(urlService.generateShortLink(request), HttpStatus.OK);
        }
        catch(UrlException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{originalUrl}")
    public ResponseEntity <?> redirectToOriginalUrl(@PathVariable String originalUrl){
        return new ResponseEntity<>(urlService.getDecodedUrl(originalUrl), HttpStatus.FOUND);
    }

    @GetMapping("/get/{originalUrl}")
    public void getFullUrl(HttpServletResponse response, @PathVariable String originalUrl) throws IOException {
        response.sendRedirect(urlService.getDecodedUrl(originalUrl));
    }

    @GetMapping("/get/get/{originalUrl}")
    public RedirectView getFullUrl1(@PathVariable String originalUrl){
        RedirectView redirectView = new RedirectView();

       redirectView.setUrl( urlService.getDecodedUrl(originalUrl));
       return redirectView;
    }

}
