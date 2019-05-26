package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.objectMother.BuscaPlanetaPayloadDtoObjectMother;
import org.junit.Test;

public class ValidaQuantidadePorPaginaTest {

    private PlanetaService planetaServiceMock = new PlanetaService(null, null);

    @Test
    public void naoDeveLancarExceptionQuantidadePorPaginaDePayloadMaiorQueUm() {
        planetaServiceMock.validaQuantidadePorPagina(BuscaPlanetaPayloadDtoObjectMother.buscaComQuantidadePorPaginaMaiorQueUm());
    }

    @Test
    public void naoDeveLancarExceptionQuantidadePorPaginaDePayloadIgualAUm() {
        planetaServiceMock.validaQuantidadePorPagina(BuscaPlanetaPayloadDtoObjectMother.buscaComQuantidadePorPaginaIgualAUm());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionQuantidadePorPaginaDePayloadMenorQueUm() {
        planetaServiceMock.validaQuantidadePorPagina(BuscaPlanetaPayloadDtoObjectMother.buscaComQuantidadePorPaginaMenorAUm());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptioPayloadNulo() {
        planetaServiceMock.validaQuantidadePorPagina(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionQuantidadePorPaginaDePayloadNulo() {
        BuscaPlanetaPayloadDto planetaPayloadDto = new BuscaPlanetaPayloadDto();
        planetaPayloadDto.setQuantidadePorPagina(null);
        planetaServiceMock.validaQuantidadePorPagina(planetaPayloadDto);
    }
}
