package br.com.leandro.starwarsapi.domain;

import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaPayloadDtoObjectMother;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterPlanetaPayloadDtoToPlanetaExistente {

    private PlanetaDtoMapper planetaDtoMapper;
    @Before
    public void setup() {
        planetaDtoMapper = new PlanetaDtoMapper();
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComTodosOsCamposPreenchidos() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        PlanetaPayloadDto planetPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();

        Planeta planetaConvertido = planetaDtoMapper.from(planetPayloadDto, planeta);
        assertThat(planetaConvertido.getId()).isEqualTo(planeta.getId());
        assertThat(planetaConvertido.getNome()).isEqualTo(planetPayloadDto.getNome());
        assertThat(planetaConvertido.getClima()).isEqualTo(planetPayloadDto.getClima());
        assertThat(planetaConvertido.getTerreno()).isEqualTo(planetPayloadDto.getTerreno());
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
        assertThat(planetaConvertido.getVersao()).isEqualTo(planeta.getVersao());
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComSomenteNomePreenchido() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        PlanetaPayloadDto planetPayloadDto = PlanetaPayloadDtoObjectMother.somenteNomePreenchidoEdicao();

        Planeta planetaConvertido = planetaDtoMapper.from(planetPayloadDto, planeta);
        assertThat(planetaConvertido.getId()).isEqualTo(planeta.getId());
        assertThat(planetaConvertido.getNome()).isEqualTo(planetPayloadDto.getNome());
        assertThat(planetaConvertido.getClima()).isEqualTo(planeta.getClima());
        assertThat(planetaConvertido.getTerreno()).isEqualTo(planeta.getTerreno());
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
        assertThat(planetaConvertido.getVersao()).isEqualTo(planeta.getVersao());
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComSomenteClimaPreenchido() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        PlanetaPayloadDto planetPayloadDto = PlanetaPayloadDtoObjectMother.somenteClimaPreenchidoEdicao();

        Planeta planetaConvertido = planetaDtoMapper.from(planetPayloadDto, planeta);
        assertThat(planetaConvertido.getId()).isEqualTo(planeta.getId());
        assertThat(planetaConvertido.getNome()).isEqualTo(planeta.getNome());
        assertThat(planetaConvertido.getClima()).isEqualTo(planetPayloadDto.getClima());
        assertThat(planetaConvertido.getTerreno()).isEqualTo(planeta.getTerreno());
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
        assertThat(planetaConvertido.getVersao()).isEqualTo(planeta.getVersao());
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComSomenteTerrenoPreenchido() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        PlanetaPayloadDto planetPayloadDto = PlanetaPayloadDtoObjectMother.somenteTerrenoPreenchido();

        Planeta planetaConvertido = planetaDtoMapper.from(planetPayloadDto, planeta);
        assertThat(planetaConvertido.getId()).isEqualTo(planeta.getId());
        assertThat(planetaConvertido.getNome()).isEqualTo(planeta.getNome());
        assertThat(planetaConvertido.getClima()).isEqualTo(planeta.getClima());
        assertThat(planetaConvertido.getTerreno()).isEqualTo(planetPayloadDto.getTerreno());
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
        assertThat(planetaConvertido.getVersao()).isEqualTo(planeta.getVersao());
    }

    @Test
    public void deveConverterPlanetaPayloadDtoToPlanetaComTodosOsCamposNaoPreenchidos() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        Planeta planetaConvertido = planetaDtoMapper.from(planetaPayloadDto, planeta);
        assertThat(planetaConvertido.getId()).isEqualTo(planeta.getId());
        assertThat(planetaConvertido.getNome()).isNull();
        assertThat(planetaConvertido.getClima()).isNull();
        assertThat(planetaConvertido.getTerreno()).isNull();
        assertThat(planetaConvertido.getQuantidadeAparicaoEmFilmes()).isEqualTo(planeta.getQuantidadeAparicaoEmFilmes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionSePlanetaNulo() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();
        planetaDtoMapper.from(planetaPayloadDto, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionSePlanetaPayloadDtoNulo() {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        planetaDtoMapper.from(null, planeta);
    }
}
