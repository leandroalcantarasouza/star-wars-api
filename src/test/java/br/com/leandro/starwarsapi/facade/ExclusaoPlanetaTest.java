package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.adapter.SwapiApiAdapter;
import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.PlanetaDtoMapper;
import br.com.leandro.starwarsapi.service.PlanetaService;
import br.com.leandro.starwarsapi.validator.JavaxValidator;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExclusaoPlanetaTest {

    @Mock
    private PlanetaDtoMapper planetaDtoMapperMock;
    @Mock
    private PlanetaService planetaServiceMock;
    @Mock
    private JavaxValidator<Planeta> planetJavaxValidatorMock;
    @Mock
    private SwapiApiAdapter swapiApiAdapterMock;

    @InjectMocks
    private PlanetFacade planetFacade;


}
