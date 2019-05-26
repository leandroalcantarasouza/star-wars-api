package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.repository.PlanetaCrudRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@RunWith(MockitoJUnitRunner.class)
public class SalvarPlanetaServiceTest {

    @Mock
    private PlanetaCrudRepository planetaCrudRepositoryMock;

    @InjectMocks
    private PlanetaService planetaService;

    @Test
    public void deveChamarPlanetaCrudRepository() {
        Planeta planetaASerEditado = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaCrudRepositoryMock.save(planetaASerEditado)).willReturn(planetaASerEditado);
        Planeta planetaResponse = planetaService.salvarPlaneta(planetaASerEditado);
        assertThat(planetaResponse).isEqualTo(planetaASerEditado);
        then(planetaCrudRepositoryMock).should(only()).save(planetaASerEditado);
    }
}
