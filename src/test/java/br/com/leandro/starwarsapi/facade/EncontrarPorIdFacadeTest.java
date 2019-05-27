package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.PlanetaDtoMapper;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.objectMother.PlanetaDtoObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.service.PlanetaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

@RunWith(MockitoJUnitRunner.class)
public class EncontrarPorIdFacadeTest {

    @Mock
    private PlanetaService planetaService;

    @Mock
    private PlanetaDtoMapper planetaDtoMapper;

    @InjectMocks
    private PlanetaFacade planetaFacade;

    @Test
    public void deveEncontrarEConverterPlanetaEncontrado() {
        final String idPlaneta = "5ce77035cb20895c33fcd329";
        Planeta planetaEncontrado = PlanetaObjectMother.planetaSalvoBatata();
        PlanetaDto planetaEncontradoConvertido = PlanetaDtoObjectMother.planetaSalvoBatata();
        given(planetaService.encontrarPorId(idPlaneta)).willReturn(Optional.of(planetaEncontrado));
        given(planetaDtoMapper.from(planetaEncontrado)).willReturn(planetaEncontradoConvertido);

        Optional<PlanetaDto> planetaResponse = planetaFacade.encontrarPorId(idPlaneta);

        assertThat(planetaResponse.isPresent()).isTrue();
        assertThat(planetaResponse.get().getId()).isEqualTo(planetaEncontradoConvertido.getId());
        assertThat(planetaResponse.get().getNome()).isEqualTo(planetaEncontradoConvertido.getNome());
        assertThat(planetaResponse.get().getClima()).isEqualTo(planetaEncontradoConvertido.getClima());
        assertThat(planetaResponse.get().getTerreno()).isEqualTo(planetaEncontradoConvertido.getTerreno());
        assertThat(planetaResponse.get().getQuantidadeAparicaoFilmes()).isEqualTo(planetaEncontradoConvertido.getQuantidadeAparicaoFilmes());

        then(planetaService).should(only()).encontrarPorId(idPlaneta);
        then(planetaDtoMapper).should(only()).from(planetaEncontrado);
    }

    @Test
    public void deveEncontrarENaoConverterPlanetaNaoEncontrado() {
        final String idPlaneta = "5ce77035cb20895c33fcd329";
        Planeta planetaEncontrado = PlanetaObjectMother.planetaSalvoBatata();
        given(planetaService.encontrarPorId(idPlaneta)).willReturn(Optional.ofNullable(null));
        Optional<PlanetaDto> planetaResponse = planetaFacade.encontrarPorId(idPlaneta);
        assertThat(planetaResponse.isPresent()).isFalse();

        then(planetaService).should(only()).encontrarPorId(idPlaneta);
        then(planetaDtoMapper).should(never()).from(planetaEncontrado);
    }

}
