package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.repository.PlanetRepository;
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
public class TotalPlanetasPorFiltroServiceTest {

    @Mock
    private PlanetRepository planetaRepository;

    @InjectMocks
    private PlanetaService planetaService;

    @Test
    public void deveChamarPlanetaRepository() {
        final String termoBusca = "batata";
        final Long resultadoTotal = 10L;
        given(planetaRepository.totalPlanetasPorFiltro(termoBusca)).willReturn(resultadoTotal);
        Long totalPlanetasPorFiltro = planetaService.totalPlanetasPorFiltro(termoBusca);
        assertThat(totalPlanetasPorFiltro).isEqualTo(resultadoTotal);
        then(planetaRepository).should(only()).totalPlanetasPorFiltro(termoBusca);
    }
}
