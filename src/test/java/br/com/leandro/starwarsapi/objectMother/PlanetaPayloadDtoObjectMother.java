package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;

public class PlanetaPayloadDtoObjectMother {

    public static PlanetaPayloadDto comTodosOsCamposPreenchidos() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setClima("frio");
        planetaPayloadDto.setNome("batata");
        planetaPayloadDto.setTerreno("montanhoso");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto somenteNomePreenchido() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setNome("batata");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto somenteClimaPreenchido() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setClima("frio");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto somenteTerrenoPreenchido() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setTerreno("montanhoso");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto comTodosOsCamposPreenchidosEdicao() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setClima("quente");
        planetaPayloadDto.setNome("potato");
        planetaPayloadDto.setTerreno("escorregadio");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto somenteNomePreenchidoEdicao() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setNome("potato");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto somenteClimaPreenchidoEdicao() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setClima("quente");
        return planetaPayloadDto;
    }

    public static PlanetaPayloadDto somenteTerrenoPreenchidoEdicao() {
        PlanetaPayloadDto planetaPayloadDto = new PlanetaPayloadDto();
        planetaPayloadDto.setTerreno("escorregadio");
        return planetaPayloadDto;
    }

}
