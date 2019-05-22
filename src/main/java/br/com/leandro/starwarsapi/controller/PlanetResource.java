package br.com.leandro.starwarsapi.controller;

import br.com.leandro.starwarsapi.dto.PlanetDto;
import br.com.leandro.starwarsapi.dto.PlanetPayloadDto;
import br.com.leandro.starwarsapi.facade.PlanetFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class PlanetResource extends BaseVersionOneRestController {

    private PlanetFacade planetFacade;

    public PlanetResource(PlanetFacade planetFacade) {
        this.planetFacade = planetFacade;
    }

    @PostMapping("/planetas")
    public ResponseEntity<PlanetDto> salvarPlaneta(@RequestBody PlanetPayloadDto planetPayloadDto) {
        final PlanetDto planetDto = planetFacade.savePlanet(planetPayloadDto);
        return new ResponseEntity<>(planetDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetDto> excluirPlaneta(@PathVariable String idPlaneta) {
        planetFacade.excluirPlaneta(idPlaneta);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/planetas/{idPlaneta}")
    public ResponseEntity<PlanetDto> excluirPlaneta(@RequestBody PlanetPayloadDto planetaPayloadDto,
                                                    @PathVariable String idPlaneta) {
        PlanetDto planetDto = planetFacade.editarPlaneta(planetaPayloadDto, idPlaneta);
        return new ResponseEntity<>(planetDto, OK);
    }
}
