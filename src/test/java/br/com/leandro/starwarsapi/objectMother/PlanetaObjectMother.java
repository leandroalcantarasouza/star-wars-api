package br.com.leandro.starwarsapi.objectMother;

import br.com.leandro.starwarsapi.domain.Planeta;

public class PlanetaObjectMother {

    public static Planeta planetaBatata() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(0L);
        planeta.setNome("batata");
        planeta.setId("5ce77035cb20895c33fcd329");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        return planeta;
    }

    public static Planeta planetaBatata2() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(0L);
        planeta.setNome("batata2");
        planeta.setId("5ce77035cb20895c33fcd330");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        return planeta;
    }

    public static Planeta planetaBatata3() {
        Planeta planeta = new Planeta();
        planeta.setQuantidadeAparicaoEmFilmes(0L);
        planeta.setNome("batata3");
        planeta.setId("5ce77035cb20895c33fcd331");
        planeta.setClima("frio");
        planeta.setTerreno("montanhoso");
        return planeta;
    }

}
