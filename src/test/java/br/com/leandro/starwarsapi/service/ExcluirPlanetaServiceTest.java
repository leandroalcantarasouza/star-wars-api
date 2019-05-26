package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.repository.PlanetaCrudRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.only;

@RunWith(MockitoJUnitRunner.class)
public class ExcluirPlanetaServiceTest {

    @Mock
    private PlanetaCrudRepository planetaCrudRepositoryMock;

    @InjectMocks
    private PlanetaService planetaService;

    @Test
    public void deveChamarPlanetaCrudRepository() {
        Planeta planetaASerExcluido = PlanetaObjectMother.planetaSalvoBatata();
        willDoNothing().given(planetaCrudRepositoryMock).deleteById(planetaASerExcluido.getId());
        planetaService.excluirPlaneta(planetaASerExcluido.getId());
        then(planetaCrudRepositoryMock).should(only()).deleteById(planetaASerExcluido.getId());
    }
}
