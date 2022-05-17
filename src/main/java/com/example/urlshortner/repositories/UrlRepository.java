package com.example.urlshortner.repositories;

import com.example.urlshortner.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
// Optional<Url> findUrlById(Long id);

 Url findUrlByOriginalUrl(String url);

    Url findUrlByShortenedUrl(String url);
    //    void findByUrlInternal(String url);

}
