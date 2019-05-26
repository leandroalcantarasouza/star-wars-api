package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class EncontrarPlanetaPorNomeTest extends MongoTestBase {

    @Autowired
    private PlanetaCrudRepository planetaCrudRepository;

    @Autowired PlanetRepository planetRepository;

    private Planeta planetaSalvo;

    @Before
    public void setup() {
        planetaSalvo = planetaCrudRepository.save(PlanetaObjectMother.planetaSalvoBatata());
    }

    @Test
    public void deveEncontrarPlanetaNaoDesconsiderandoProprioPlanetaPassado() {
        assertThat(planetRepository.encontraPlanetaPorNome(planetaSalvo, false)).isTrue();
    }

    @Test
    public void naoDeveEncontrarPlanetaDesconsiderandoProprioPlanetaPassado() {
        assertThat(planetRepository.encontraPlanetaPorNome(planetaSalvo, true)).isFalse();
    }
}
