package br.com.leandro.starwarsapi.repository;

import br.com.leandro.starwarsapi.domain.Planeta;
import org.springframework.data.repository.CrudRepository;

public interface PlanetaCrudRepository extends CrudRepository<Planeta, String> {
}
