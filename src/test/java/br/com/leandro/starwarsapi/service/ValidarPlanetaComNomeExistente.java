package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.exception.PlanetaExistenteException;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.repository.PlanetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@RunWith(MockitoJUnitRunner.class)
public class ValidarPlanetaComNomeExistente {

    @Mock
    private PlanetRepository planetaRepository;

    @InjectMocks
    private PlanetaService planetaService;

    @Test
    public void naoDeveLancarExceptionCasoNaoSejaEncontradoPlanetaExistente() {
        Planeta planetaABuscar = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaRepository.encontraPlanetaPorNome(eq(planetaABuscar),anyBoolean())).willReturn(Boolean.FALSE);
        planetaService.validaPlanetaComNomeExistente(planetaABuscar, Boolean.FALSE);
        then(planetaRepository).should(only()).encontraPlanetaPorNome(eq(planetaABuscar), anyBoolean());
    }

    @Test(expected = PlanetaExistenteException.class)
    public void deveLancarExceptionCasoNaoSejaEncontradoPlanetaExistente() {
        Planeta planetaABuscar = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaRepository.encontraPlanetaPorNome(eq(planetaABuscar),anyBoolean())).willReturn(Boolean.TRUE);
        planetaService.validaPlanetaComNomeExistente(planetaABuscar, Boolean.TRUE);
    }

}
