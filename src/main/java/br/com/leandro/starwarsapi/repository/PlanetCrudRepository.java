package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.domain.Planeta;
import org.springframework.data.repository.CrudRepository;

public interface PlanetCrudRepository extends CrudRepository<Planeta, String> {
}
