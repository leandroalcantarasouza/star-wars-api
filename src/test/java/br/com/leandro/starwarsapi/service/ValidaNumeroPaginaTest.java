package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.objectMother.BuscaPlanetaPayloadDtoObjectMother;
import org.junit.Test;

public class ValidaNumeroPaginaTest {

    private PlanetaService planetaServiceMock = new PlanetaService(null, null);


    @Test
    public void naoDeveLancarExceptionPaginaDePayloadMaiorQueZero() {
        planetaServiceMock.validaNumeroPagina(BuscaPlanetaPayloadDtoObjectMother.buscaComPaginaMaiorQueZero());
    }

    @Test
    public void naoDeveLancarExceptionPaginaDePayloadIgualAZero() {
        planetaServiceMock.validaNumeroPagina(BuscaPlanetaPayloadDtoObjectMother.buscaComPaginaIgualAZero());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionPaginaDePayloadMenorQueZero() {
        planetaServiceMock.validaNumeroPagina(BuscaPlanetaPayloadDtoObjectMother.buscaComPaginaMenorAZero());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionPaginaDePayloadNulo() {
        planetaServiceMock.validaNumeroPagina(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExceptionPaginaDePayloadComQuantidadePaginaNulo() {
        BuscaPlanetaPayloadDto planetaPayloadDto = new BuscaPlanetaPayloadDto();
        planetaPayloadDto.setNumeroPagina(null);
        planetaServiceMock.validaNumeroPagina(planetaPayloadDto);
    }
}
