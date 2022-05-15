package com.example.urlshortner.services;

import com.example.urlshortner.dtos.requests.UrlDto;
import com.example.urlshortner.dtos.responses.UrlResponse;
import com.example.urlshortner.exception.UrlException;
import com.example.urlshortner.models.Url;

public interface UrlService {
    UrlResponse generateShortLink(UrlDto urlDto) throws UrlException;

    String getEncodedUrl(String url);
    void deleteShortLink(Url url);

    String updateShortLink(String shortLink);

    String getDecodedUrl(String url);
}
