package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterPlanetaToPlanetaDto {

    private PlanetaDtoMapper planetaDtoMapper;

    @Before
    public void setup() {
        planetaDtoMapper = new PlanetaDtoMapper();
    }

    @Test
    public void deveConverterPlanetaToPlanetaDtoComTodosOsCamposPreenchidos() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();

        PlanetaDto planetaDto = planetaDtoMapper.from(planeta);
        assertThat(planetaDto.getId()).isEqualTo(planeta.getId());
        assertThat(planetaDto.getNome()).isEqualTo(planeta.getNome());
        assertThat(planetaDto.getClima()).isEqualTo(planeta.getClima());
        assertThat(planetaDto.getTerreno()).isEqualTo(planeta.getTerreno());
        assertThat(planetaDto.getQuantidadeAparicaoFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
    }

    @Test
    public void deveConverterPlanetaToPlanetaDtoComSomenteNomePreenchido() {
        Planeta planeta = PlanetaObjectMother.somenteNomePreenchido();

        PlanetaDto planetaDto = planetaDtoMapper.from(planeta);
        assertThat(planetaDto.getId()).isNull();
        assertThat(planetaDto.getNome()).isEqualTo(planeta.getNome());
        assertThat(planetaDto.getClima()).isNull();
        assertThat(planetaDto.getTerreno()).isNull();
        assertThat(planetaDto.getQuantidadeAparicaoFilmes()).isNull();
    }

    @Test
    public void deveConverterPlanetaPlanetaDtoComSomenteClimaPreenchido() {
        Planeta planeta = PlanetaObjectMother.somenteClimaPreenchido();

        PlanetaDto planetaDto = planetaDtoMapper.from(planeta);
        assertThat(planetaDto.getId()).isNull();
        assertThat(planetaDto.getNome()).isNull();
        assertThat(planetaDto.getClima()).isEqualTo(planeta.getClima());
        assertThat(planetaDto.getTerreno()).isNull();
        assertThat(planetaDto.getQuantidadeAparicaoFilmes()).isNull();
    }

    @Test
    public void deveConverterPlanetaToPlanetaDtoComSomenteTerrenoPreenchido() {
        Planeta planeta = PlanetaObjectMother.somenteTerrenoPreenchido();

        PlanetaDto planetaDto = planetaDtoMapper.from(planeta);
        assertThat(planetaDto.getId()).isNull();
        assertThat(planetaDto.getNome()).isNull();
        assertThat(planetaDto.getClima()).isNull();
        assertThat(planetaDto.getTerreno()).isEqualTo(planeta.getTerreno());
        assertThat(planetaDto.getQuantidadeAparicaoFilmes()).isNull();
    }

    @Test
    public void deveConverterPlanetaToPlanetaDtoComSomenteAparicaoFilmePreenchido() {
        Planeta planeta = PlanetaObjectMother.somenteAparicaoEmFilmesPreenchido();

        PlanetaDto planetaDto = planetaDtoMapper.from(planeta);
        assertThat(planetaDto.getId()).isNull();
        assertThat(planetaDto.getNome()).isNull();
        assertThat(planetaDto.getClima()).isNull();
        assertThat(planetaDto.getTerreno()).isNull();
        assertThat(planetaDto.getQuantidadeAparicaoFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
    }


    @Test
    public void deveConverterPlanetaToPlanetaDtoComTodosOsCamposNaoPreenchidos() {
        PlanetaDto planetaDto = planetaDtoMapper.from(new Planeta());
        assertThat(planetaDto.getId()).isNull();
        assertThat(planetaDto.getNome()).isNull();
        assertThat(planetaDto.getClima()).isNull();
        assertThat(planetaDto.getTerreno()).isNull();
        assertThat(planetaDto.getQuantidadeAparicaoFilmes()).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoSePlanetaENulo() {
        planetaDtoMapper.from((Planeta) null);
    }
}
