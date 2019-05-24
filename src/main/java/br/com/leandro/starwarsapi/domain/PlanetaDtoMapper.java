package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetDto;
import br.com.leandro.starwarsapi.dto.PlanetPayloadDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanetaDtoMapper {

    public Planeta from(PlanetPayloadDto planetPayloadDto) {
        Planeta planeta = new Planeta();
        planeta.setClima(planetPayloadDto.getClima());
        planeta.setNome(planetPayloadDto.getNome());
        planeta.setTerreno(planetPayloadDto.getTerreno());
        return planeta;
    }

    public Planeta from(PlanetPayloadDto planetPayloadDto, Planeta planeta) {
        planeta.setClima(planetPayloadDto.getClima());
        planeta.setNome(planetPayloadDto.getNome());
        planeta.setTerreno(planetPayloadDto.getTerreno());
        return planeta;
    }

    public PlanetDto from(Planeta planeta) {
        PlanetDto planetDto = new PlanetDto();
        planetDto.setId(planeta.getId());
        planetDto.setClima(planeta.getClima());
        planetDto.setNome(planeta.getNome());
        planetDto.setTerreno(planeta.getTerreno());
        planetDto.setQuantidadeAparicaoFilmes(planeta.getQuantidadeAparicaoEmFilmes());
        return planetDto;
    }

    public List<PlanetDto> from(List<Planeta> planetasPorFiltro) {
        return planetasPorFiltro.stream().map(this::from).collect(Collectors.toList());
    }
}
