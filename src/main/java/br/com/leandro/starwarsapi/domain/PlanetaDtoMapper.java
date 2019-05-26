package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PlanetaDtoMapper {

    public Planeta from(PlanetaPayloadDto planetaPayloadDto) {
        if(Objects.isNull(planetaPayloadDto)) {
            throw new IllegalArgumentException("Planeta não pode ser nulo");
        }
        Planeta planeta = new Planeta();
        planeta.setClima(planetaPayloadDto.getClima());
        planeta.setNome(planetaPayloadDto.getNome());
        planeta.setTerreno(planetaPayloadDto.getTerreno());
        return planeta;
    }

    public Planeta from(PlanetaPayloadDto planetaPayloadDto, Planeta planeta) {
        if(Objects.isNull(planeta) || Objects.isNull(planetaPayloadDto)) {
            throw new IllegalArgumentException("Planeta não pode ser nulo");
        }
        planeta.setClima(planetaPayloadDto.getClima());
        planeta.setNome(planetaPayloadDto.getNome());
        planeta.setTerreno(planetaPayloadDto.getTerreno());
        return planeta;
    }

    public PlanetaDto from(Planeta planeta) {
        if(Objects.isNull(planeta)) {
            throw new IllegalArgumentException("Planeta não pode ser nulo");
        }
        PlanetaDto planetaDto = new PlanetaDto();
        planetaDto.setId(planeta.getId());
        planetaDto.setClima(planeta.getClima());
        planetaDto.setNome(planeta.getNome());
        planetaDto.setTerreno(planeta.getTerreno());
        planetaDto.setQuantidadeAparicaoFilmes(planeta.getQuantidadeAparicaoEmFilmes());
        return planetaDto;
    }

    public List<PlanetaDto> from(List<Planeta> planetasPorFiltro) {
        return planetasPorFiltro.stream().map(this::from).collect(Collectors.toList());
    }
}
