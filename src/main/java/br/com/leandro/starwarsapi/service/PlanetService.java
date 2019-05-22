package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.exception.PlanetaExistenteException;
import br.com.leandro.starwarsapi.exception.PlanetaNaoEncontradoException;
import br.com.leandro.starwarsapi.repository.PlanetCrudRepository;
import br.com.leandro.starwarsapi.repository.PlanetRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private PlanetCrudRepository planetCrudRepository;
    private PlanetRepository planetRepository;

    public PlanetService(PlanetCrudRepository planetCrudRepository, PlanetRepository planetRepository) {
        this.planetCrudRepository = planetCrudRepository;
        this.planetRepository = planetRepository;
    }

    public Planeta savePlanet(Planeta planeta) {
        return planetCrudRepository.save(planeta);
    }

    public void validaPlanetaComNomeExistenteInsercao(Planeta planeta) {
        if(planetRepository.encontraPlanetaPorNomeInclusao(planeta)) {
            throw new PlanetaExistenteException();
        }
    }

    public void excluirPlaneta(String idPlaneta) {
        planetCrudRepository.deleteById(idPlaneta);
    }

    public Planeta descobrirPorId(String idPlaneta) {
        return planetCrudRepository.findById(idPlaneta).orElseThrow(PlanetaNaoEncontradoException::new);
    }

    public Planeta editarPlaneta(Planeta planetaASerAtualizado) {
        return planetCrudRepository.save(planetaASerAtualizado);
    }

    public void validaPlanetaComNomeExistenteEdicao(Planeta planetaASerAtualizado) {
        if(planetRepository.encontraPlanetaPorNomeEdicao(planetaASerAtualizado)) {
            throw new PlanetaExistenteException();
        }
    }
}
