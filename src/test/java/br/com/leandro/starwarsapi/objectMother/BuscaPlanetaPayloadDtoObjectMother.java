package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;

public class BuscaPlanetaPayloadDtoObjectMother {

    public static BuscaPlanetaPayloadDto buscaComPaginaMaiorQueZero() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNumeroPagina(1);
        buscaPlanetaPayloadDto.setNome("batata");
        return buscaPlanetaPayloadDto;
    }

    public static BuscaPlanetaPayloadDto buscaComPaginaIgualAZero() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNumeroPagina(0);
        buscaPlanetaPayloadDto.setNome("batata");
        return buscaPlanetaPayloadDto;
    }

    public static BuscaPlanetaPayloadDto buscaComPaginaMenorAZero() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNumeroPagina(-1);
        buscaPlanetaPayloadDto.setNome("batata");
        return buscaPlanetaPayloadDto;
    }

    public static BuscaPlanetaPayloadDto buscaComQuantidadePorPaginaMaiorQueUm() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setQuantidadePorPagina(2);
        buscaPlanetaPayloadDto.setNome("batata");
        return buscaPlanetaPayloadDto;
    }

    public static BuscaPlanetaPayloadDto buscaComQuantidadePorPaginaIgualAUm() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setQuantidadePorPagina(1);
        buscaPlanetaPayloadDto.setNome("batata");
        return buscaPlanetaPayloadDto;
    }

    public static BuscaPlanetaPayloadDto buscaComQuantidadePorPaginaMenorAUm() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setQuantidadePorPagina(0);
        buscaPlanetaPayloadDto.setNome("batata");
        return buscaPlanetaPayloadDto;
    }
}
