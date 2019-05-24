package br.com.leandro.starwarsapi.adapter;

import java.util.List;

public class PlanetaSwapiResponse {

    Integer count;
    List<Response> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Response> getResults() {
        return results;
    }

    public void setResults(List<Response> results) {
        this.results = results;
    }
}