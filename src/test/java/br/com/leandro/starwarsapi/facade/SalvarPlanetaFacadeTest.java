package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.adapter.SwapiApiAdapter;
import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.PlanetaDtoMapper;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.exception.PlanetaExistenteException;
import br.com.leandro.starwarsapi.objectMother.PlanetaDtoObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaPayloadDtoObjectMother;
import br.com.leandro.starwarsapi.service.PlanetaService;
import br.com.leandro.starwarsapi.validator.JavaxValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalvarPlanetaFacadeTest {

    @Mock
    private PlanetaDtoMapper planetaDtoMapper;
    @Mock
    private PlanetaService planetaService;
    @Mock
    private JavaxValidator<Planeta> planetJavaxValidator;
    @Mock
    private SwapiApiAdapter swapiApiAdapter;

    @InjectMocks
    private PlanetaFacade planetaFacade;

    @Test
    public void deveSalvarCorretamente() {
        InOrder inOrder = Mockito.inOrder(planetaDtoMapper,
                planetJavaxValidator,
                swapiApiAdapter,
                planetaService,
                planetaDtoMapper);

        Integer quantidadeFilmes = 5;

        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidos();
        Planeta planetaASalvar = PlanetaObjectMother.planetaASalvarBatata();
        given(planetaDtoMapper.from(planetaPayloadDto)).willReturn(planetaASalvar);
        willDoNothing().given(planetJavaxValidator).validate(planetaASalvar);
        willDoNothing().given(planetaService).validaPlanetaComNomeExistente(planetaASalvar, Boolean.FALSE);
        given(swapiApiAdapter.recuperaAparicaoEmFilmesDe(planetaASalvar)).willReturn(quantidadeFilmes);

        Planeta planetaComFilmes = new Planeta();
        planetaComFilmes.setNome(planetaASalvar.getNome());
        planetaComFilmes.setClima(planetaASalvar.getClima());
        planetaComFilmes.setTerreno(planetaASalvar.getTerreno());
        planetaComFilmes.setQuantidadeAparicaoEmFilmes(5);

        Planeta planetaSalvo = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaService.salvarPlaneta(planetaComFilmes)).willReturn(planetaSalvo);
        PlanetaDto planetaDtoAposSalvar = PlanetaDtoObjectMother.planetaSalvoBatata();
        given(planetaDtoMapper.from(planetaSalvo)).willReturn(planetaDtoAposSalvar);

        PlanetaDto planetaDtoResponse = planetaFacade.salvarPlaneta(planetaPayloadDto);
        assertThat(planetaDtoResponse.getId()).isEqualTo(planetaDtoAposSalvar.getId());
        assertThat(planetaDtoResponse.getNome()).isEqualTo(planetaDtoAposSalvar.getNome());
        assertThat(planetaDtoResponse.getClima()).isEqualTo(planetaDtoAposSalvar.getClima());
        assertThat(planetaDtoResponse.getTerreno()).isEqualTo(planetaDtoAposSalvar.getTerreno());
        assertThat(planetaDtoResponse.getQuantidadeAparicaoFilmes()).isEqualTo(planetaDtoAposSalvar.getQuantidadeAparicaoFilmes());

        inOrder.verify(planetaDtoMapper).from(planetaPayloadDto);
        inOrder.verify(planetJavaxValidator).validate(planetaASalvar);
        inOrder.verify(planetaService).validaPlanetaComNomeExistente(planetaASalvar, Boolean.FALSE);
        inOrder.verify(swapiApiAdapter).recuperaAparicaoEmFilmesDe(planetaASalvar);
        inOrder.verify(planetaService).salvarPlaneta(planetaComFilmes);
        inOrder.verify(planetaDtoMapper).from(planetaSalvo);

        verifyNoMoreInteractions(planetaDtoMapper, planetJavaxValidator, planetaService, swapiApiAdapter);
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveLancarConstraintViolationExceptionEmCasoDeErroDeValidacao() {

        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidos();
        Planeta planetaASalvar = PlanetaObjectMother.planetaASalvarBatata();
        given(planetaDtoMapper.from(planetaPayloadDto)).willReturn(planetaASalvar);
        willThrow(ConstraintViolationException.class).given(planetJavaxValidator).validate(planetaASalvar);

        try {
            planetaFacade.salvarPlaneta(planetaPayloadDto);
        } catch (ConstraintViolationException exc) {
            then(planetaService).should(never()).validaPlanetaComNomeExistente(any(Planeta.class), anyBoolean());
            then(swapiApiAdapter).should(never()).recuperaAparicaoEmFilmesDe(any(Planeta.class));
            then(planetaService).should(never()).salvarPlaneta(any(Planeta.class));
            then(planetaDtoMapper).should(never()).from(any(Planeta.class));
            throw exc;
        }

        fail("Deveria ter lancado ConstraintViolantionException");
    }

    @Test(expected = PlanetaExistenteException.class)
    public void deveLancarExceptionEmCasoDeErroDeValidacaoComNomeExistente() {

        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidos();
        Planeta planetaASalvar = PlanetaObjectMother.planetaASalvarBatata();
        given(planetaDtoMapper.from(planetaPayloadDto)).willReturn(planetaASalvar);
        willDoNothing().given(planetJavaxValidator).validate(planetaASalvar);
        willThrow(PlanetaExistenteException.class).given(planetaService).validaPlanetaComNomeExistente(planetaASalvar, Boolean.FALSE);

        try {
            planetaFacade.salvarPlaneta(planetaPayloadDto);
        } catch (PlanetaExistenteException exc) {
            then(swapiApiAdapter).should(never()).recuperaAparicaoEmFilmesDe(any(Planeta.class));
            then(planetaService).should(never()).salvarPlaneta(any(Planeta.class));
            then(planetaDtoMapper).should(never()).from(any(Planeta.class));
            throw exc;
        }

        fail("Deveria ter lancado PlanetaException");
    }
}
