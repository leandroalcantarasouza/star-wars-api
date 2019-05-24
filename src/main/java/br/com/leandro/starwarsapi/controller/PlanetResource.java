package br.com.leandro.starwarsapi.controller;

import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetDto;
import br.com.leandro.starwarsapi.dto.PlanetPayloadDto;
import br.com.leandro.starwarsapi.facade.PlanetFacade;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
public class PlanetResource extends BaseVersionOneRestController {

    private PlanetFacade planetFacade;

    public PlanetResource(PlanetFacade planetFacade) {
        this.planetFacade = planetFacade;
    }

    @PostMapping("/planetas")
    public ResponseEntity<PlanetDto> salvarPlaneta(@RequestBody PlanetPayloadDto planetPayloadDto) {
        final PlanetDto planetDto = planetFacade.salvarPlaneta(planetPayloadDto);
        UriComponents ucb =
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/v1/planetas/{id}")
                        .buildAndExpand(planetDto.getId());

        URI uriLocation = ucb.toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, uriLocation.toASCIIString());
        return new ResponseEntity<>(planetDto, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetDto> excluirPlaneta(@PathVariable String idPlaneta) {
        planetFacade.excluirPlaneta(idPlaneta);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetDto> editarPlaneta(@RequestBody PlanetPayloadDto planetaPayloadDto,
                                                    @PathVariable String idPlaneta) {
        PlanetDto planetDto = planetFacade.editarPlaneta(planetaPayloadDto, idPlaneta);
        return new ResponseEntity<>(planetDto, OK);
    }

    @GetMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetDto> encontrarPorId(@PathVariable String idPlaneta) {
        Optional<PlanetDto> planetDto = planetFacade.encontrarPorId(idPlaneta);
        return planetDto.map(dto -> new ResponseEntity<>(dto, OK)).orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/planetas")
    public ResponseEntity<Slice<PlanetDto>> encontrarPorNome(BuscaPlanetaPayloadDto buscaPlaneta) {
        Slice<PlanetDto> paginaPlanetasEncontrados = planetFacade.buscarPlaneta(buscaPlaneta);
        ResponseEntity<Slice<PlanetDto>> respostaHttp;
        if(paginaPlanetasEncontrados.hasContent()) {
            respostaHttp = new ResponseEntity<>(paginaPlanetasEncontrados, OK);
        } else {
            respostaHttp = new ResponseEntity<>(paginaPlanetasEncontrados, NO_CONTENT);
        }
        return respostaHttp;
    }
}
