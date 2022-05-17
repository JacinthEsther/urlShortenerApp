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

    @GetMapping("/{shortUrl}")
    public ResponseEntity <?> redirectToOriginalUrl(@PathVariable String shortUrl){
        return new ResponseEntity<>(urlService.getDecodedUrl(shortUrl), HttpStatus.FOUND);
    }

    @GetMapping("/get/{shortUrl}")
    public void getFullUrl(HttpServletResponse response, @PathVariable String shortUrl) throws IOException {
        response.sendRedirect(urlService.getDecodedUrl(shortUrl));
    }

    @GetMapping("/get/get/{shortUrl}")
    public RedirectView getFullUrl1(@PathVariable String shortUrl){
        RedirectView redirectView = new RedirectView();

       redirectView.setUrl( urlService.getDecodedUrl(shortUrl));
       return redirectView;
    }

    @DeleteMapping("/{url}")
    public void deleteUrl(@PathVariable String url) throws UrlException {
        urlService.deleteShortLink(url);
    }


    @PatchMapping("/customiseUrl")
    public ResponseEntity<?> customiseShortLink(@RequestParam String shortLink, @RequestParam String customiseUrl)
            throws UrlException {
        return new ResponseEntity<>(urlService.updateShortLink(shortLink,customiseUrl), HttpStatus.ACCEPTED);
    }

}
