package com.example.urlshortner.services;

import com.example.urlshortner.dtos.requests.UrlDto;
import com.example.urlshortner.dtos.responses.UrlResponse;
import com.example.urlshortner.exception.UrlException;
import com.example.urlshortner.models.Url;
import com.example.urlshortner.repositories.UrlRepository;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@Transactional
public class UrlServiceImpl implements UrlService {

    String encodedUrl="";
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public UrlResponse generateShortLink(UrlDto urlDto) throws UrlException {
        UrlResponse response = new UrlResponse();

//       Optional <Url> checkUrl= Optional.ofNullable(urlRepository.findUrlByOriginalUrl(urlDto.getUrl()));
//       if(checkUrl.isPresent()){
//           getEncodedUrl(urlDto.getUrl());
//       }

           if (!StringUtils.isEmpty(urlDto.getUrl())) {
               if (!isUrlValid(urlDto.getUrl())) {

                   throw new UrlException("Not a Valid Url");
               } else {
                   String encodedUrl = encodeUrl(urlDto.getUrl());
                   Url url = new Url();
                   url.setOriginalUrl(urlDto.getUrl());
                   url.setCreationDate(LocalDateTime.now());
                   url.setShortenedUrl(encodedUrl);

                   Url savedUrl = urlRepository.save(url);


                   response.setOriginalUrl(savedUrl.getOriginalUrl());
                   response.setShortenedUrl(savedUrl.getShortenedUrl());

               }
               return response;

       }
        throw new UrlException("url cannot be empty");

    }


    private boolean isUrlValid(String url) {

            String regex = "((http|https)://)(www.)?"
                    + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                    + "{2,256}\\.[a-z]"
                    + "{2,6}\\b([-a-zA-Z0-9@:%"
                    + "._\\+~#?&//=]*)";


            Pattern p = Pattern.compile(regex);

            Matcher m = p.matcher(url);

        return m.matches();
    }

    private String encodeUrl(String url) {

        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return encodedUrl;
    }



    @Override
    public String getEncodedUrl(String url) {
   Url urls = urlRepository.findUrlByOriginalUrl(url);
   return urls.getShortenedUrl();
    }

    @Override
    public void deleteShortLink(String url) throws UrlException {

    Optional<Url> foundUrl= Optional.ofNullable(urlRepository.findUrlByShortenedUrl(url));
     if (foundUrl.isPresent()){
         urlRepository.delete(foundUrl.get());
     }
     else {
         Optional<Url> foundUrl1= Optional.ofNullable(urlRepository.findUrlByOriginalUrl(url));
         if(foundUrl1.isPresent()) {
             urlRepository.delete(foundUrl.get());
         }
      else throw new UrlException("url not found");
     }
    }

    @Override
    public String updateShortLink(String shortLink, String customizedLink) throws UrlException {
        Optional<Url> foundUrl = Optional.ofNullable(urlRepository.findUrlByShortenedUrl(shortLink));

        if(foundUrl.isEmpty()){
            throw new UrlException("url");
        }

        foundUrl.get().setShortenedUrl(customizedLink);

        urlRepository.save(foundUrl.get());
        return foundUrl.get().getShortenedUrl();

    }

    @Override
    public String getDecodedUrl(String url) {
        Url urls = urlRepository.findUrlByShortenedUrl(url);
       return urls.getOriginalUrl();

    }

    @Override
    public void delete(Url url) {
        urlRepository.delete(url);
    }

    @Override
    public void deleteUser(UrlDto request) {
        Url url = urlRepository.findUrlByOriginalUrl(request.getUrl());
        urlRepository.delete(url);
    }


}
