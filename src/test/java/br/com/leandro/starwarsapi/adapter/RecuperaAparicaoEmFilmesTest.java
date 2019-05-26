package br.com.leandro.starwarsapi.adapter;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.exception.SwapiApiForaDoArException;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import br.com.leandro.starwarsapi.objectMother.PlanetaSwapiResponseObjectMother;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecuperaAparicaoEmFilmesTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SwapiApiAdapter apiAdapter;

    @Autowired
    private SwapiApiAdapter swapiApiAdapter;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    private PlanetaSwapiResponse planetaSwapiResponseMock;

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void deveChamarApiSwapiComPlanetaContendoFilme() throws URISyntaxException, JsonProcessingException {

        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        planetaSwapiResponseMock = PlanetaSwapiResponseObjectMother.planetaBatataSwapiComDoisFilmes();

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://swapi.co/api/planets?search="+planeta.getNome())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(planetaSwapiResponseMock))
                );

        Long resultado = swapiApiAdapter.recuperaAparicaoEmFilmesDe(planeta);
        mockServer.verify();
        assertThat(resultado).isEqualTo(2);
    }

    @Test
    public void deveChamarApiSwapiComPlanetaNaoContendoFilme() throws URISyntaxException, JsonProcessingException {

        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        planetaSwapiResponseMock = PlanetaSwapiResponseObjectMother.planetaBatataComNenhumFilme();

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://swapi.co/api/planets?search="+planeta.getNome())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(planetaSwapiResponseMock))
                );

        Long resultado = swapiApiAdapter.recuperaAparicaoEmFilmesDe(planeta);
        mockServer.verify();
        assertThat(resultado).isEqualTo(0);
    }

    @Test
    public void deveChamarApiSwapiComPlanetaNaoEncontrado() throws URISyntaxException, JsonProcessingException {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        planetaSwapiResponseMock = PlanetaSwapiResponseObjectMother.planetaNaoEncontrado();

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://swapi.co/api/planets?search="+planeta.getNome())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(planetaSwapiResponseMock))
                );

        Long resultado = swapiApiAdapter.recuperaAparicaoEmFilmesDe(planeta);
        mockServer.verify();
        assertThat(resultado).isEqualTo(0);
    }

    @Test(expected = SwapiApiForaDoArException.class)
    public void deveJogarExcecaoChamadaSwapiComErro() throws URISyntaxException, JsonProcessingException {
        Planeta planeta = PlanetaObjectMother.planetaSalvoBatata();
        planetaSwapiResponseMock = PlanetaSwapiResponseObjectMother.planetaNaoEncontrado();

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://swapi.co/api/planets?search="+planeta.getNome())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                );

        swapiApiAdapter.recuperaAparicaoEmFilmesDe(planeta);
        mockServer.verify();
    }
}
