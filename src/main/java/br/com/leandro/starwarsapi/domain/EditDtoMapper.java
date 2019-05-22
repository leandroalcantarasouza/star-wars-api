package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetPayloadDto;
import org.springframework.stereotype.Component;

@Component
public class EditDtoMapper {

    public Planeta from(PlanetPayloadDto planetPayloadDto, Planeta planeta) {
        planeta.setClima(planetPayloadDto.getClima());
        planeta.setNome(planetPayloadDto.getNome());
        planeta.setTerreno(planetPayloadDto.getTerreno());
        return planeta;
    }
}
