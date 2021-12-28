package com.guilherme.springboot2.client;

import com.guilherme.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 3);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 3);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());

        // Anime kingdom = Anime.builder().name("kingdom").build();
        // Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
        // log.info("Saved anime {}", kingdomSaved);

        Anime samuraiChamplo = Anime.builder().name("samurai champlo").build();
        ResponseEntity<Anime> samuraiChamploSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamplo, createJsonHeaders()),
                Anime.class);
        log.info("Saved anime {}", samuraiChamploSaved);

        Anime animeToBeUpdated = samuraiChamploSaved.getBody();
        animeToBeUpdated.setName("Samurai Champlo");

        ResponseEntity<Void> samuraiChamploUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeaders()),
                Void.class);
        log.info(samuraiChamploUpdated);

        ResponseEntity<Void> samuraiChamploDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class, animeToBeUpdated.getId());
        log.info(samuraiChamploSaved);

    }
    private static HttpHeaders createJsonHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
