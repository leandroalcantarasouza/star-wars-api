package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterListaPlanetaToPlanetaDto {

    private PlanetaDtoMapper planetaDtoMapper;

    @Before
    public void setup() {
        planetaDtoMapper = new PlanetaDtoMapper();
    }

    @Test
    public void deveConverterPlanetasToPlanetaDtoComTodosOsCamposPreenchidos() {
        Planeta planetaMock = PlanetaObjectMother.planetaSalvoBatata();
        List<Planeta> planetas = Lists.newArrayList(planetaMock);
        List<PlanetaDto> planetasDto = planetaDtoMapper.from(planetas);
        assertThat(planetasDto.size()).isEqualTo(1);
        Planeta planetaDto = planetas.get(0);
        assertThat(planetaDto.getId()).isEqualTo(planetaMock.getId());
        assertThat(planetaDto.getNome()).isEqualTo(planetaMock.getNome());
        assertThat(planetaDto.getClima()).isEqualTo(planetaMock.getClima());
        assertThat(planetaDto.getTerreno()).isEqualTo(planetaMock.getTerreno());
        assertThat(planetaDto.getQuantidadeAparicaoEmFilmes()).isEqualTo(planetaMock.getQuantidadeAparicaoEmFilmes());
    }
}
