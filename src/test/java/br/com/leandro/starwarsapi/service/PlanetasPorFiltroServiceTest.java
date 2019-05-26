package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.repository.PlanetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@RunWith(MockitoJUnitRunner.class)
public class PlanetasPorFiltroServiceTest {

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetaService planetaService;

    @Test
    public void deveVerificarChamadaARepository() {
        final PageRequest pagina = PageRequest.of(0, 3);
        final String termoBusca = "Batata";
        final List<Planeta> planetasEncontrados = newArrayList(planetaSalvoBatata(), planetaSalvoBatata2(), planetaSalvoBatata3());
        given(planetRepository.planetasPorFiltro(pagina, termoBusca)).willReturn(planetasEncontrados);
        List<Planeta> planetasResponse = planetaService.planetasPorFiltro(pagina, termoBusca);
        assertThat(planetasResponse).isEqualTo(planetasEncontrados);
        then(planetRepository).should(only()).planetasPorFiltro(pagina, termoBusca);
    }
}
