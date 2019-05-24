package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.exception.PlanetaExistenteException;
import br.com.leandro.starwarsapi.exception.PlanetaNaoEncontradoException;
import br.com.leandro.starwarsapi.repository.PlanetCrudRepository;
import br.com.leandro.starwarsapi.repository.PlanetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<Planeta> encontrarPorId(String idPlaneta) {
        return planetCrudRepository.findById(idPlaneta);
    }

    public Long totalPlanetasPorFiltro(PageRequest pageRequest, String nome) {
        return planetRepository.totalPlanetasPorFiltro(pageRequest, nome);
    }

    public List<Planeta> planetasPorFiltro(PageRequest pageRequest, String nome) {
        return planetRepository.planetasPorFiltro(pageRequest, nome);
    }

    public void validaNumeroPagina(BuscaPlanetaPayloadDto buscaPlaneta) {
        if(Objects.nonNull(buscaPlaneta) &&
                Objects.nonNull(buscaPlaneta.getNumeroPagina()) &&
                buscaPlaneta.getNumeroPagina() < 0) {
            throw new IllegalArgumentException("Número de página não pode ser menor que 0");
        }
    }

    public void validaQuantidadePorPagina(BuscaPlanetaPayloadDto buscaPlaneta) {
        if(Objects.nonNull(buscaPlaneta) &&
                Objects.nonNull(buscaPlaneta.getQuantidadePorPagina()) &&
                buscaPlaneta.getQuantidadePorPagina() < 1) {
            throw new IllegalArgumentException("Quantiade de elementos por página não pode ser menor que 1");
        }
    }
}
