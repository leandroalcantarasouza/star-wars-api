package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.objectMother.PlanetaObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanetasPorFiltroTest extends MongoTestBase {

    @Autowired
    private PlanetaCrudRepository planetaCrudRepository;

    @Autowired PlanetRepository planetRepository;

    private Planeta planetaBatataUm;
    private Planeta planetaBatataDois;
    private Planeta planetaBatataTres;

    @Before
    public void setup() {
        planetaBatataUm = planetaCrudRepository.save(PlanetaObjectMother.planetaBatata());
        planetaBatataDois = planetaCrudRepository.save(PlanetaObjectMother.planetaBatata2());
        planetaBatataTres = planetaCrudRepository.save(PlanetaObjectMother.planetaBatata3());
    }

    @Test
    public void deveEncontrarPlanetasContendoResultadosNaPagina() {
        PageRequest pageRequest = PageRequest.of(0,3);
        List<Planeta> batatasResult = planetRepository.planetasPorFiltro(pageRequest, "batata");
        assertThat(batatasResult.size()).isEqualTo(3);
        assertThat(batatasResult).containsExactly(planetaBatataUm, planetaBatataDois, planetaBatataTres);
    }

    @Test
    public void naoDeveEncontrarPlanetasContendoResultadosForaDaPagina() {
        PageRequest pageRequest = PageRequest.of(1,3);
        List<Planeta> batatasResult = planetRepository.planetasPorFiltro(pageRequest, "batata");
        assertThat(batatasResult.size()).isEqualTo(0);
    }

}
