package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalPlanetasPorFiltroTest extends MongoTestBase {

    @Autowired
    private PlanetaCrudRepository planetaCrudRepository;

    @Autowired PlanetRepository planetRepository;

    @Before
    public void setup() {
        planetaCrudRepository.save(PlanetaObjectMother.planetaBatata());
        planetaCrudRepository.save(PlanetaObjectMother.planetaBatata2());
        planetaCrudRepository.save(PlanetaObjectMother.planetaBatata3());
    }

    @Test
    public void deveEncontrarTotalDePlanetasComOutrosPlanetasComNomeAposTermo() {
        assertThat(planetRepository.totalPlanetasPorFiltro("batata")).isEqualTo(3);
    }

    @Test
    public void deveEncontrarTotalDePlanetasProcurandoPorNomeDeUmPlaneta() {
        assertThat(planetRepository.totalPlanetasPorFiltro("batata2")).isEqualTo(1);
    }

}
