package br.com.leandro.starwarsapi.controller;

import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.facade.PlanetFacade;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PlanetaDto> salvarPlaneta(@RequestBody PlanetaPayloadDto planetaPayloadDto) {
        final PlanetaDto planetaDto = planetFacade.salvarPlaneta(planetaPayloadDto);
        UriComponents ucb =
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/v1/planetas/{id}")
                        .buildAndExpand(planetaDto.getId());

        URI uriLocation = ucb.toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, uriLocation.toASCIIString());
        return new ResponseEntity<>(planetaDto, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetaDto> excluirPlaneta(@PathVariable String idPlaneta) {
        planetFacade.excluirPlaneta(idPlaneta);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetaDto> editarPlaneta(@RequestBody PlanetaPayloadDto planetaPayloadDto,
                                                    @PathVariable String idPlaneta) {
        PlanetaDto planetaDto = planetFacade.editarPlaneta(planetaPayloadDto, idPlaneta);
        return new ResponseEntity<>(planetaDto, OK);
    }

    @GetMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetaDto> encontrarPorId(@PathVariable String idPlaneta) {
        Optional<PlanetaDto> planetDto = planetFacade.encontrarPorId(idPlaneta);
        return planetDto.map(dto -> new ResponseEntity<>(dto, OK)).orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/planetas")
    public ResponseEntity<Slice<PlanetaDto>> encontrarPorNome(BuscaPlanetaPayloadDto buscaPlaneta) {
        Slice<PlanetaDto> paginaPlanetasEncontrados = planetFacade.buscarPlaneta(buscaPlaneta);
        ResponseEntity<Slice<PlanetaDto>> respostaHttp;
        if(paginaPlanetasEncontrados.hasContent()) {
            respostaHttp = new ResponseEntity<>(paginaPlanetasEncontrados, OK);
        } else {
            respostaHttp = new ResponseEntity<>(paginaPlanetasEncontrados, NO_CONTENT);
        }
        return respostaHttp;
    }
}
