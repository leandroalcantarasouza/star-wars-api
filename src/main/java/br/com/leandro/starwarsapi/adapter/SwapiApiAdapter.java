package br.com.leandro.starwarsapi.adapter;

import br.com.leandro.starwarsapi.controller.handler.DefaultExceptionHandler;
import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.exception.SwapiApiForaDoArException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.Optional;

@Component
public class SwapiApiAdapter {

    private String swapiApiBaseUri;
    private RestTemplate restTemplate;
    private static final String PLANETS_URI = "/planets";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public SwapiApiAdapter(@Value("${swapi.api.uri}") String swapiApiBaseUri,
                           RestTemplate restTemplate) {
        this.swapiApiBaseUri = swapiApiBaseUri;
        this.restTemplate = restTemplate;
    }

    public Long recuperaAparicaoEmFilmesDe(Planeta planeta) {

        Objects.requireNonNull(planeta);
        Objects.requireNonNull(planeta.getNome());

        Long quantidadeAparicao = 0L;

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(swapiApiBaseUri)
                .path(PLANETS_URI).query("search={name}").buildAndExpand(planeta.getNome()).toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<PlanetaSwapiResponse> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<PlanetaSwapiResponse> response = restTemplate.getForEntity(endpoint, PlanetaSwapiResponse.class, request);
            PlanetaSwapiResponse planetaSwapiResponse = response.getBody();
            if(response.getStatusCode().is2xxSuccessful() && CollectionUtils.isNotEmpty(Objects.requireNonNull(planetaSwapiResponse).getResults())) {
                Optional<Response> results = planetaSwapiResponse.getResults().stream().findFirst();
                quantidadeAparicao = results.map(responseDto -> responseDto.films.stream().count()).get();
            }
        } catch(HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException exception) {
            LOGGER.error("Erro ao chamar a api do swapi", exception);
            throw new SwapiApiForaDoArException();
        }

        return quantidadeAparicao;
    }
}
