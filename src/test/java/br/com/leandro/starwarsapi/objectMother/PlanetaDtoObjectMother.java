package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.dto.PlanetaDto;

public class PlanetaDtoObjectMother {

    public static PlanetaDto planetaSalvoBatata() {
        PlanetaDto planetaDto = new PlanetaDto();
        planetaDto.setQuantidadeAparicaoFilmes(0);
        planetaDto.setNome("batata");
        planetaDto.setId("5ce77035cb20895c33fcd329");
        planetaDto.setClima("frio");
        planetaDto.setTerreno("montanhoso");
        return planetaDto;
    }

}
