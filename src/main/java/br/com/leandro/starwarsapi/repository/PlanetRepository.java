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

    public Boolean encontraPlanetaPorNomeInclusao(Planeta planeta) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").regex("^"+planeta.getNome()+"$","i"));
        Long retorno = mongoTemplate.count(query, Planeta.class);
        return retorno != 0L;
    }

    public Boolean encontraPlanetaPorNomeEdicao(Planeta planetaASerAtualizado) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").regex("^"+planetaASerAtualizado.getNome()+"$","i"));
        query.addCriteria(Criteria.where("id").ne(planetaASerAtualizado.getId()));
        Long retorno = mongoTemplate.count(query, Planeta.class);
        return retorno != 0L;
    }

    public Long totalPlanetasPorFiltro(PageRequest pageRequest, String nome) {
        Query query = filtroPadrao();
        query.with(pageRequest);
        if(StringUtils.isNotBlank(nome)) {
            query.addCriteria(Criteria.where("nome").regex("^"+nome,"i"));
        }
        return mongoTemplate.count(query, Planeta.class);
    }

    private Query filtroPadrao() {
        return new Query().with(Sort.by(Sort.Direction.DESC,"_d"));
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
