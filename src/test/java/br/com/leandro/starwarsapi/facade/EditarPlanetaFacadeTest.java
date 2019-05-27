package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.adapter.SwapiApiAdapter;
import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.PlanetaDtoMapper;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.exception.PlanetaExistenteException;
import br.com.leandro.starwarsapi.exception.PlanetaNaoEncontradoException;
import br.com.leandro.starwarsapi.objectMother.PlanetaDtoObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaPayloadDtoObjectMother;
import br.com.leandro.starwarsapi.service.PlanetaService;
import br.com.leandro.starwarsapi.validator.JavaxValidator;
import org.apache.tomcat.util.bcel.Const;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EditarPlanetaFacadeTest {

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
        InOrder inOrder = Mockito.inOrder(planetaService,
                planetaDtoMapper,
                planetJavaxValidator,
                swapiApiAdapter,
                planetaService,
                planetaDtoMapper);

        Long quantidadeFilmes = 5L;
        String planetaId = "5ce77035cb20895c33fcd331";
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();

        Planeta planetaAEditar = PlanetaObjectMother.planetaAEditarBatata();
        given(planetaService.encontrarPorId(planetaId)).willReturn(Optional.of(planetaAEditar));
        given(planetaDtoMapper.from(planetaPayloadDto, planetaAEditar)).willReturn(planetaAEditar);
        willDoNothing().given(planetJavaxValidator).validate(planetaAEditar);
        willDoNothing().given(planetaService).validaPlanetaComNomeExistente(planetaAEditar, Boolean.TRUE);
        given(swapiApiAdapter.recuperaAparicaoEmFilmesDe(planetaAEditar)).willReturn(quantidadeFilmes);

        Planeta planetaComFilmes = new Planeta();
        planetaComFilmes.setNome(planetaAEditar.getNome());
        planetaComFilmes.setId(planetaAEditar.getId());
        planetaComFilmes.setClima(planetaAEditar.getClima());
        planetaComFilmes.setTerreno(planetaAEditar.getTerreno());
        planetaComFilmes.setQuantidadeAparicaoEmFilmes(5L);

        Planeta planetaEditado = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaService.editarPlaneta(planetaComFilmes)).willReturn(planetaEditado);

        PlanetaDto planetaDtoAposEditar = PlanetaDtoObjectMother.planetaSalvoBatata();
        given(planetaDtoMapper.from(planetaEditado)).willReturn(planetaDtoAposEditar);

        PlanetaDto planetaDtoResponse = planetaFacade.editarPlaneta(planetaPayloadDto, planetaId);
        assertThat(planetaDtoResponse.getId()).isEqualTo(planetaDtoAposEditar.getId());
        assertThat(planetaDtoResponse.getNome()).isEqualTo(planetaDtoAposEditar.getNome());
        assertThat(planetaDtoResponse.getClima()).isEqualTo(planetaDtoAposEditar.getClima());
        assertThat(planetaDtoResponse.getTerreno()).isEqualTo(planetaDtoAposEditar.getTerreno());
        assertThat(planetaDtoResponse.getQuantidadeAparicaoFilmes()).isEqualTo(planetaDtoAposEditar.getQuantidadeAparicaoFilmes());

        inOrder.verify(planetaService).encontrarPorId(planetaId);
        inOrder.verify(planetaDtoMapper).from(planetaPayloadDto, planetaAEditar);
        inOrder.verify(planetJavaxValidator).validate(planetaAEditar);
        inOrder.verify(planetaService).validaPlanetaComNomeExistente(planetaAEditar, Boolean.TRUE);
        inOrder.verify(swapiApiAdapter).recuperaAparicaoEmFilmesDe(planetaAEditar);
        inOrder.verify(planetaService).editarPlaneta(planetaComFilmes);
        inOrder.verify(planetaDtoMapper).from(planetaEditado);

        verifyNoMoreInteractions(planetaService, planetaDtoMapper, planetJavaxValidator, swapiApiAdapter);
    }


    @Test(expected = PlanetaNaoEncontradoException.class)
    public void deveLancarPlanetaNaoEncontradoExceptionEmCasoDeIdNaoEncontrado() {

        String planetaId = "5ce77035cb20895c33fcd331";
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();
        given(planetaService.encontrarPorId(planetaId)).willReturn(Optional.ofNullable(null));

        try {
            planetaFacade.editarPlaneta(planetaPayloadDto, planetaId);
        } catch (PlanetaNaoEncontradoException exc) {
            then(planetaDtoMapper).should(never()).from(any(PlanetaPayloadDto.class), any(Planeta.class));
            then(planetJavaxValidator).should(never()).validate(any(Planeta.class));
            then(planetaService).should(never()).validaPlanetaComNomeExistente(any(Planeta.class), anyBoolean());
            then(swapiApiAdapter).should(never()).recuperaAparicaoEmFilmesDe(any(Planeta.class));
            then(planetaService).should(never()).editarPlaneta(any(Planeta.class));
            then(planetaDtoMapper).should(never()).from(any(Planeta.class));
            throw exc;
        }

        fail("Deveria ter lancado PlanetaNaoEncontradoException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveLancarConstraintViolantionExceptionEmCasoDeErroDeValidacao() {

        String planetaId = "5ce77035cb20895c33fcd331";
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();
        Planeta planetaAEditar = PlanetaObjectMother.planetaAEditarBatata();
        given(planetaService.encontrarPorId(planetaId)).willReturn(Optional.of(planetaAEditar));
        given(planetaDtoMapper.from(planetaPayloadDto, planetaAEditar)).willReturn(planetaAEditar);
        willThrow(ConstraintViolationException.class).given(planetJavaxValidator).validate(planetaAEditar);

        try {
            planetaFacade.editarPlaneta(planetaPayloadDto, planetaId);
        } catch (ConstraintViolationException exc) {
            then(planetaService).should(never()).validaPlanetaComNomeExistente(any(Planeta.class), anyBoolean());
            then(swapiApiAdapter).should(never()).recuperaAparicaoEmFilmesDe(any(Planeta.class));
            then(planetaService).should(never()).editarPlaneta(any(Planeta.class));
            then(planetaDtoMapper).should(never()).from(any(Planeta.class));
            throw exc;
        }

        fail("Deveria ter lancado ConstraintViolationException");
    }

    @Test(expected = PlanetaExistenteException.class)
    public void deveLancarExceptionEmCasoDeErroDeValidacaoComNomeExistente() {

        String planetaId = "5ce77035cb20895c33fcd331";
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();
        Planeta planetaAEditar = PlanetaObjectMother.planetaAEditarBatata();
        given(planetaService.encontrarPorId(planetaId)).willReturn(Optional.of(planetaAEditar));
        given(planetaDtoMapper.from(planetaPayloadDto, planetaAEditar)).willReturn(planetaAEditar);
        given(planetaDtoMapper.from(planetaPayloadDto, planetaAEditar)).willReturn(planetaAEditar);
        willDoNothing().given(planetJavaxValidator).validate(planetaAEditar);
        willThrow(PlanetaExistenteException.class).given(planetaService).validaPlanetaComNomeExistente(planetaAEditar, Boolean.TRUE);

        try {
            planetaFacade.editarPlaneta(planetaPayloadDto, planetaId);
        } catch (PlanetaExistenteException exc) {
            then(swapiApiAdapter).should(never()).recuperaAparicaoEmFilmesDe(any(Planeta.class));
            then(planetaService).should(never()).editarPlaneta(any(Planeta.class));
            then(planetaDtoMapper).should(never()).from(any(Planeta.class));
            throw exc;
        }

        fail("Deveria ter lancado PlanetaExistenteException");
    }

}
