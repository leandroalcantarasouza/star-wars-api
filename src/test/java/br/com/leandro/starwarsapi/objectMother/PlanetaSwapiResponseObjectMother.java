package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.adapter.PlanetaSwapiResponse;
import org.assertj.core.util.Lists;

public class PlanetaSwapiResponseObjectMother {

    public static PlanetaSwapiResponse planetaBatataSwapiComDoisFilmes() {
        PlanetaSwapiResponse planetaSwapiResponse = new PlanetaSwapiResponse();
        planetaSwapiResponse.setCount(1);
        planetaSwapiResponse.setResults(Lists.newArrayList(ResponseObjectMother.responseCom2Filmes()));
        return planetaSwapiResponse;
    }

    public static PlanetaSwapiResponse planetaNaoEncontrado() {
        PlanetaSwapiResponse planetaSwapiResponse = new PlanetaSwapiResponse();
        planetaSwapiResponse.setCount(0);
        return planetaSwapiResponse;
    }

    public static PlanetaSwapiResponse planetaBatataComNenhumFilme() {
        PlanetaSwapiResponse planetaSwapiResponse = new PlanetaSwapiResponse();
        planetaSwapiResponse.setCount(1);
        planetaSwapiResponse.setResults(Lists.newArrayList());
        return planetaSwapiResponse;
    }
}
