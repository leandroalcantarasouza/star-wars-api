package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.repository.PlanetaCrudRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.only;

@RunWith(MockitoJUnitRunner.class)
public class FindByIdPlanetaServiceTest {

    @Mock
    private PlanetaCrudRepository planetaCrudRepositoryMock;

    @InjectMocks
    private PlanetaService planetaService;

    @Test
    public void deveChamarPlanetaCrudRepository() {
        Planeta planetaASerEncontrado = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaCrudRepositoryMock.findById(planetaASerEncontrado.getId())).willReturn(Optional.of(planetaASerEncontrado));
        Optional<Planeta> planetaResponse = planetaService.encontrarPorId(planetaASerEncontrado.getId());
        assertThat(planetaResponse.get()).isEqualTo(planetaASerEncontrado);
        then(planetaCrudRepositoryMock).should(only()).findById(planetaASerEncontrado.getId());
    }
}
