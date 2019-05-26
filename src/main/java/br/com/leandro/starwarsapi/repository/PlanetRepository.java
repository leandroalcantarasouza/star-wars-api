package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.domain.Planeta;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanetRepository {

    private MongoTemplate mongoTemplate;

    public PlanetRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Boolean encontraPlanetaPorNome(Planeta planeta, Boolean desconsiderarProprioPlaneta) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").regex("^"+planeta.getNome()+"$","i"));
        if(desconsiderarProprioPlaneta) {
            query.addCriteria(Criteria.where("id").ne(planeta.getId()));
        }
        Long retorno = mongoTemplate.count(query, Planeta.class);
        return retorno != 0L;
    }

    public Long totalPlanetasPorFiltro(String nome) {
        Query query = filtroPadrao();
        if(StringUtils.isNotBlank(nome)) {
            query.addCriteria(Criteria.where("nome").regex("^"+nome,"i"));
        }
        return mongoTemplate.count(query, Planeta.class);
    }

    private Query filtroPadrao() {
        return new Query().with(Sort.by(Sort.Direction.ASC,"nome"));
    }

    public List<Planeta> planetasPorFiltro(PageRequest pageRequest, String nome) {
        Query query = filtroPadrao();
        query.with(pageRequest);
        if(StringUtils.isNotBlank(nome)) {
            query.addCriteria(Criteria.where("nome").regex("^"+nome,"i"));
        }
        return mongoTemplate.find(query, Planeta.class);
    }
}
