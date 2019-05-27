package br.com.leandro.starwarsapi.controller;

import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.facade.PlanetaFacade;
import br.com.leandro.starwarsapi.objectMother.PlanetaDtoObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaPayloadDtoObjectMother;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest(PlanetaResource.class)
@RunWith(SpringRunner.class)
public class PlanetaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @MockBean
    private PlanetaFacade planetaFacade;

    @Test
    public void deveSalvarPlaneta() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidos();
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        org.mockito.BDDMockito.given(planetaFacade.salvarPlaneta(planetaPayloadDto)).willReturn(planetaDto);

        given()
                .contentType(ContentType.JSON)
                .body(planetaPayloadDto)
                .when()
                .post("/v1/planetas")
                .then()
                .statusCode(equalTo(201))
                .and()
                .header("Location", containsString("/v1/planetas/" + planetaDto.getId()))
                .body("id", equalTo(planetaDto.getId()),
                        "terreno", equalTo(planetaDto.getTerreno()),
                        "clima", equalTo(planetaDto.getClima()),
                        "nome", equalTo(planetaDto.getNome()),
                        "quantidadeAparicaoFilmes", equalTo(planetaDto.getQuantidadeAparicaoFilmes())
                );
    }

    @Test
    public void deveEditarPlaneta() {
        PlanetaPayloadDto planetaPayloadDto = PlanetaPayloadDtoObjectMother.comTodosOsCamposPreenchidosEdicao();
        final String idPlaneta = "5ce77035cb20895c33fcd331";
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        org.mockito.BDDMockito.given(planetaFacade.editarPlaneta(planetaPayloadDto, idPlaneta)).willReturn(planetaDto);
        given()
                .contentType(ContentType.JSON)
                .body(planetaPayloadDto)
                .when()
                .patch("/v1/planetas/{idPlaneta}", idPlaneta)
                .then()
                .statusCode(equalTo(200))
                .body("id", equalTo(planetaDto.getId()),
                        "terreno", equalTo(planetaDto.getTerreno()),
                        "clima", equalTo(planetaDto.getClima()),
                        "nome", equalTo(planetaDto.getNome()),
                        "quantidadeAparicaoFilmes", equalTo(planetaDto.getQuantidadeAparicaoFilmes())
                );
    }

    @Test
    public void deveEncontrarPlanetaPorId() {
        final String idPlaneta = "5ce77035cb20895c33fcd331";
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        org.mockito.BDDMockito.given(planetaFacade.encontrarPorId(idPlaneta)).willReturn(Optional.of(planetaDto));
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/planetas/{idPlaneta}", idPlaneta)
                .then()
                .statusCode(equalTo(200))
                .and()
                .body("id", equalTo(planetaDto.getId()),
                        "terreno", equalTo(planetaDto.getTerreno()),
                        "clima", equalTo(planetaDto.getClima()),
                        "nome", equalTo(planetaDto.getNome()),
                        "quantidadeAparicaoFilmes", equalTo(planetaDto.getQuantidadeAparicaoFilmes())
                );
    }

    @Test
    public void deveRetornar404PlanetaNaoEncontradoPorId() {
        final String idPlaneta = "5ce77035cb20895c33fcd331";
        PlanetaDto planetaDto = PlanetaDtoObjectMother.planetaSalvoBatata();
        org.mockito.BDDMockito.given(planetaFacade.encontrarPorId(idPlaneta)).willReturn(Optional.ofNullable(null));
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/planetas/{idPlaneta}", idPlaneta)
                .then()
                .statusCode(equalTo(404)
                );
    }

    @Test
    public void deveExcluirPlaneta() {
        final String idPlaneta = "5ce77035cb20895c33fcd331";
        willDoNothing().given(planetaFacade).excluirPlaneta(idPlaneta);
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/v1/planetas/{idPlaneta}", idPlaneta)
                .then()
                .statusCode(equalTo(200));
    }

    @Test
    public void deveEncontrarPlanetaSemFiltro() {
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());

        PlanetaDto planeta = PlanetaDtoObjectMother.planetaSalvoBatata();
        List<PlanetaDto> planetasExpected = Lists.newArrayList(planeta);
        Page<PlanetaDto> retornoConsulta = new PageImpl<>(planetasExpected, pageRequest, 10L);

        org.mockito.BDDMockito.given(planetaFacade.buscarPlaneta(buscaPlanetaPayloadDto)).willReturn(retornoConsulta);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/planetas/")
                .then()
                .statusCode(equalTo(200))
                .and()
                .body(
                        "totalElements", equalTo(10),
                        "pageable.pageNumber", equalTo(pageRequest.getPageNumber()),
                        "pageable.pageSize", equalTo(pageRequest.getPageSize()),
                        "content[0].id", equalTo(planeta.getId()),
                        "content[0].terreno", equalTo(planeta.getTerreno()),
                        "content[0].clima", equalTo(planeta.getClima()),
                        "content[0].nome", equalTo(planeta.getNome()),
                        "content[0].quantidadeAparicaoFilmes", equalTo(planeta.getQuantidadeAparicaoFilmes())
                );
    }

    @Test
    public void deveEncontrarPlanetaComFiltro() {
        final String termoBusca = "batata";
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNome("batata");
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());
        PlanetaDto planeta = PlanetaDtoObjectMother.planetaSalvoBatata();
        List<PlanetaDto> planetasExpected = Lists.newArrayList(planeta);
        Page<PlanetaDto> retornoConsulta = new PageImpl<>(planetasExpected, pageRequest, 10L);

        org.mockito.BDDMockito.given(planetaFacade.buscarPlaneta(buscaPlanetaPayloadDto)).willReturn(retornoConsulta);
        given()
                .contentType(ContentType.JSON)
                .param("nome", termoBusca)
                .when()
                .get("/v1/planetas/")
                .then()
                .statusCode(equalTo(200))
                .and()
                .body(
                        "totalElements", equalTo(10),
                        "pageable.pageNumber", equalTo(pageRequest.getPageNumber()),
                        "pageable.pageSize", equalTo(pageRequest.getPageSize()),
                        "content[0].id", equalTo(planeta.getId()),
                        "content[0].terreno", equalTo(planeta.getTerreno()),
                        "content[0].clima", equalTo(planeta.getClima()),
                        "content[0].nome", equalTo(planeta.getNome()),
                        "content[0].quantidadeAparicaoFilmes", equalTo(planeta.getQuantidadeAparicaoFilmes())
                );
    }

    @Test
    public void deveEncontrarPlanetaComFiltroEPaginacao() {
        final String termoBusca = "batata";
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNome("batata");
        buscaPlanetaPayloadDto.setNumeroPagina(0);
        buscaPlanetaPayloadDto.setQuantidadePorPagina(1);
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());
        PlanetaDto planeta = PlanetaDtoObjectMother.planetaSalvoBatata();
        List<PlanetaDto> planetasExpected = Lists.newArrayList(planeta);
        Page<PlanetaDto> retornoConsulta = new PageImpl<>(planetasExpected, pageRequest, 10L);

        org.mockito.BDDMockito.given(planetaFacade.buscarPlaneta(buscaPlanetaPayloadDto)).willReturn(retornoConsulta);
        given()
                .contentType(ContentType.JSON)
                .param("nome", termoBusca)
                .param("numeroPagina", buscaPlanetaPayloadDto.getNumeroPagina())
                .param("quantidadePorPagina", buscaPlanetaPayloadDto.getQuantidadePorPagina())
                .when()
                .get("/v1/planetas/")
                .then()
                .statusCode(equalTo(200))
                .and()
                .body(
                        "totalElements", equalTo(10),
                        "pageable.pageNumber", equalTo(pageRequest.getPageNumber()),
                        "pageable.pageSize", equalTo(pageRequest.getPageSize()),
                        "content[0].id", equalTo(planeta.getId()),
                        "content[0].terreno", equalTo(planeta.getTerreno()),
                        "content[0].clima", equalTo(planeta.getClima()),
                        "content[0].nome", equalTo(planeta.getNome()),
                        "content[0].quantidadeAparicaoFilmes", equalTo(planeta.getQuantidadeAparicaoFilmes())
                );
    }

    @Test
    public void deveRetornarCodigoHttp204CasoNaoEncontrePlaneta() {
        final String termoBusca = "batata";
        BuscaPlanetaPayloadDto buscaPlanetaPayloadDto = new BuscaPlanetaPayloadDto();
        buscaPlanetaPayloadDto.setNome("batata");
        buscaPlanetaPayloadDto.setNumeroPagina(0);
        buscaPlanetaPayloadDto.setQuantidadePorPagina(1);
        PageRequest pageRequest = PageRequest.of(buscaPlanetaPayloadDto.getNumeroPagina(), buscaPlanetaPayloadDto.getQuantidadePorPagina());
        List<PlanetaDto> planetasExpected = Lists.newArrayList();
        Page<PlanetaDto> retornoConsulta = new PageImpl<>(planetasExpected, pageRequest, 0L);

        org.mockito.BDDMockito.given(planetaFacade.buscarPlaneta(buscaPlanetaPayloadDto)).willReturn(retornoConsulta);
        given()
                .contentType(ContentType.JSON)
                .param("nome", termoBusca)
                .param("numeroPagina", buscaPlanetaPayloadDto.getNumeroPagina())
                .param("quantidadePorPagina", buscaPlanetaPayloadDto.getQuantidadePorPagina())
                .when()
                .get("/v1/planetas/")
                .then()
                .statusCode(equalTo(204))
                .and()
                .body(
                        "totalElements", equalTo(0),
                        "pageable.pageNumber", equalTo(pageRequest.getPageNumber()),
                        "pageable.pageSize", equalTo(pageRequest.getPageSize())
                );
    }

}
