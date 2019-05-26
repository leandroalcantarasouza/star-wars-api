package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.adapter.Response;
import org.assertj.core.util.Lists;

public class ResponseObjectMother {

    public static Response responseCom2Filmes() {
        Response response = new Response();
        response.setFilms(Lists.newArrayList("Imperio da batata", "Batata contra ataca"));
        return response;
    }
}
