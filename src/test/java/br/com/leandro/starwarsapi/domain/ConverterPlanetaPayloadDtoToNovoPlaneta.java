package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.objectMother.PlanetaPayloadDtoObjectMother;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterPlanetaPayloadDtoToNovoPlaneta {

    private PlanetaDtoMapper planetaDtoMapper;
    @Before
    public void setup() {
        planetaDtoMapper = new PlanetaDtoMapper();
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComTodosOsCamposPreenchidos() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidos();

        Planeta planetaConvertido = planetaDtoMapper.from(planetaPayloadDto);
        assertThat(planetaConvertido.getId()).isNull();
        assertThat(planetaConvertido.getNome()).isEqualTo(planetaPayloadDto.getNome());
        assertThat(planetaConvertido.getClima()).isEqualTo(planetaPayloadDto.getClima());
        assertThat(planetaConvertido.getTerreno()).isEqualTo(planetaPayloadDto.getTerreno());
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isNull();
        assertThat(planetaConvertido.getVersao()).isNull();
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComSomenteNomePreenchido() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.somenteNomePreenchido();

        Planeta planetaConvertido = planetaDtoMapper.from(planetaPayloadDto);
        assertThat(planetaConvertido.getId()).isNull();
        assertThat(planetaConvertido.getNome()).isEqualTo(planetaPayloadDto.getNome());
        assertThat(planetaConvertido.getClima()).isNull();
        assertThat(planetaConvertido.getTerreno()).isNull();
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isNull();
        assertThat(planetaConvertido.getVersao()).isNull();
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComSomenteClimaPreenchido() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.somenteClimaPreenchido();

        Planeta planetaConvertido = planetaDtoMapper.from(planetaPayloadDto);
        assertThat(planetaConvertido.getId()).isNull();
        assertThat(planetaConvertido.getNome()).isNull();
        assertThat(planetaConvertido.getClima()).isEqualTo(planetaPayloadDto.getClima());
        assertThat(planetaConvertido.getTerreno()).isNull();
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isNull();
        assertThat(planetaConvertido.getVersao()).isNull();
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComSomenteTerrenoPreenchido() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.somenteTerrenoPreenchido();

        Planeta planetaConvertido = planetaDtoMapper.from(planetaPayloadDto);
        assertThat(planetaConvertido.getId()).isNull();
        assertThat(planetaConvertido.getNome()).isNull();
        assertThat(planetaConvertido.getClima()).isNull();
        assertThat(planetaConvertido.getTerreno()).isEqualTo(planetaPayloadDto.getTerreno());
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isNull();
        assertThat(planetaConvertido.getVersao()).isNull();
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComTodosOsCamposNaoPreenchidos() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        Planeta planetaConvertido = planetaDtoMapper.from(planetaPayloadDto);
        assertThat(planetaConvertido.getId()).isNull();
        assertThat(planetaConvertido.getNome()).isNull();
        assertThat(planetaConvertido.getClima()).isNull();
        assertThat(planetaConvertido.getTerreno()).isNull();
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoSePlanetaPayloadDtoENulo() {
        planetaDtoMapper.from((PlanetaPayloadDto) null);
    }

}
