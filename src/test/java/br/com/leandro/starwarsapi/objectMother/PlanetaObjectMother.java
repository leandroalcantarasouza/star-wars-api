package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.domain.Planeta;

public class PlanetaObjectMother {

    public static Planeta planetaSalvoBatata() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(0);
        planeta.setNome("batata");
        planeta.setId("5ce77035cb20895c33fcd329");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        planeta.setQuantidadeAparicaoEmFilmes(5);
        return planeta;
    }

    public static Planeta planetaSalvoBatata2() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(0);
        planeta.setNome("batata2");
        planeta.setId("5ce77035cb20895c33fcd330");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        planeta.setQuantidadeAparicaoEmFilmes(5);
        return planeta;
    }

    public static Planeta planetaSalvoBatata3() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(0);
        planeta.setNome("batata3");
        planeta.setId("5ce77035cb20895c33fcd331");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        planeta.setQuantidadeAparicaoEmFilmes(5);
        return planeta;
    }

    public static Planeta planetaAEditarBatata() {
        Planeta planeta = new Planeta();
        planeta.setId("5ce77035cb20895c33fcd331");
        planeta.setQuantidadeAparicaoEmFilmes(0);
        planeta.setNome("batata");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        return planeta;
    }

    public static Planeta planetaASalvarBatata() {
        Planeta planeta = new Planeta();
        planeta.setNome("batata");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        return planeta;
    }

    public static Planeta somenteNomePreenchido() {
        Planeta planeta = new Planeta();
        planeta.setNome("batata");
        return planeta;
    }

    public static Planeta somenteClimaPreenchido() {
        Planeta planeta = new Planeta();
        planeta.setClima("frio");
        return planeta;
    }

    public static Planeta somenteTerrenoPreenchido() {
        Planeta planeta = new Planeta();
        planeta.setTerreno("montanhoso");
        return planeta;
    }


    public static Planeta somenteAparicaoEmFilmesPreenchido() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(5);
        return planeta;
    }

}
