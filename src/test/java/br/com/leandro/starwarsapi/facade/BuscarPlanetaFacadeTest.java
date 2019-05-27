package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.PlanetaDtoMapper;
import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.objectMother.PlanetaDtoObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.service.PlanetaService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuscarPlanetaFacadeTest {

    @Mock
    private PlanetaDtoMapper planetaDtoMapper;
    @Mock
    private PlanetaService planetaService;
    @InjectMocks
    private PlanetaFacade planetaFacade;

    @Test
    public void deveConsultarSemFiltro() {

        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());

        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        List<Planeta> planetasExpected = Lists.newArrayList(planeta);
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        Long quantidadeTotal = 1L;

        given(planetaService.totalPlanetasPorFiltro(null)).willReturn(quantidadeTotal);
        given(planetaService.planetasPorFiltro(pageRequest, null)).willReturn(planetasExpected);
        given(planetaDtoMapper.from(planetasExpected)).willReturn(Lists.newArrayList(planetaDto));

        Page<PlanetaDto> planetaDtoResponse = planetaFacade.buscarPlaneta(null);

        assertThat(planetaDtoResponse.getTotalElements()).isEqualTo(quantidadeTotal);
        assertThat(planetaDtoResponse.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(planetaDtoResponse.getPageable().getPageSize()).isEqualTo(10);
        assertThat(planetaDtoResponse.getContent()).containsExactly(planetaDto);

        then(planetaService).should(never()).validaNumeroPagina(any(BuscaPlanetaPayloadDto.class));
        then(planetaService).should(never()).validaQuantidadePorPagina(any(BuscaPlanetaPayloadDto.class));
        then(planetaService).should(times(1)).totalPlanetasPorFiltro(isNull());
        then(planetaService).should(times(1)).planetasPorFiltro(eq(pageRequest), isNull());
        then(planetaDtoMapper).should(only()).from(planetasExpected);

    }

    @Test
    public void deveConsultarComFiltroSemPaginacao() {

        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNome("batata");
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());

        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        List<Planeta> planetasExpected = Lists.newArrayList(planeta);
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        Long quantidadeTotal = 1L;

        given(planetaService.totalPlanetasPorFiltro(buscaPlanetaPayloadDto.getNome())).willReturn(quantidadeTotal);
        given(planetaService.planetasPorFiltro(pageRequest, buscaPlanetaPayloadDto.getNome())).willReturn(planetasExpected);
        given(planetaDtoMapper.from(planetasExpected)).willReturn(Lists.newArrayList(planetaDto));

        Page<PlanetaDto> planetaDtoResponse = planetaFacade.buscarPlaneta(buscaPlanetaPayloadDto);

        assertThat(planetaDtoResponse.getTotalElements()).isEqualTo(quantidadeTotal);
        assertThat(planetaDtoResponse.getContent()).containsExactly(planetaDto);
        assertThat(planetaDtoResponse.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(planetaDtoResponse.getPageable().getPageSize()).isEqualTo(10);


        then(planetaService).should(times(1)).validaNumeroPagina(buscaPlanetaPayloadDto);
        then(planetaService).should(times(1)).validaQuantidadePorPagina(buscaPlanetaPayloadDto);
        then(planetaService).should(times(1)).totalPlanetasPorFiltro(buscaPlanetaPayloadDto.getNome());
        then(planetaService).should(times(1)).planetasPorFiltro(pageRequest, buscaPlanetaPayloadDto.getNome());
        then(planetaDtoMapper).should(times(1)).from(planetasExpected);

    }

    @Test
    public void deveConsultarComFiltroComPaginacao() {

        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNome("batata");
        buscaPlanetaPayloadDto.setQuantidadePorPagina(20);
        buscaPlanetaPayloadDto.setNumeroPagina(0);
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());

        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        List<Planeta> planetasExpected = Lists.newArrayList(planeta);
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        Long quantidadeTotal = 1L;

        given(planetaService.totalPlanetasPorFiltro(buscaPlanetaPayloadDto.getNome())).willReturn(quantidadeTotal);
        given(planetaService.planetasPorFiltro(pageRequest, buscaPlanetaPayloadDto.getNome())).willReturn(planetasExpected);
        given(planetaDtoMapper.from(planetasExpected)).willReturn(Lists.newArrayList(planetaDto));

        Page<PlanetaDto> planetaDtoResponse = planetaFacade.buscarPlaneta(buscaPlanetaPayloadDto);

        assertThat(planetaDtoResponse.getTotalElements()).isEqualTo(quantidadeTotal);
        assertThat(planetaDtoResponse.getContent()).containsExactly(planetaDto);
        assertThat(planetaDtoResponse.getPageable().getPageNumber()).isEqualTo(buscaPlanetaPayloadDto.getNumeroPagina());
        assertThat(planetaDtoResponse.getPageable().getPageSize()).isEqualTo(buscaPlanetaPayloadDto.getQuantidadePorPagina());

        then(planetaService).should(times(1)).validaNumeroPagina(buscaPlanetaPayloadDto);
        then(planetaService).should(times(1)).validaQuantidadePorPagina(buscaPlanetaPayloadDto);
        then(planetaService).should(times(1)).totalPlanetasPorFiltro(buscaPlanetaPayloadDto.getNome());
        then(planetaService).should(times(1)).planetasPorFiltro(pageRequest, buscaPlanetaPayloadDto.getNome());
        then(planetaDtoMapper).should(times(1)).from(planetasExpected);

    }
}
