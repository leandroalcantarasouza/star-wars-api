package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.service.PlanetaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExclusaoPlanetaFacadeTest {

    @Mock
    private PlanetaService planetaService;

    @InjectMocks
    private PlanetaFacade planetaFacade;

    @Test
    public void deveVerificarChamadaParaService() {
        final String idPlaneta = "5ce77035cb20895c33fcd329";
        willDoNothing().given(planetaService).excluirPlaneta(idPlaneta);
        planetaFacade.excluirPlaneta(idPlaneta);
        then(planetaService).should(only()).excluirPlaneta(idPlaneta);
    }

}
