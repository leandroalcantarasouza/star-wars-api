package br.com.leandro.starwarsapi.service;

import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.exception.PlanetaExistenteException;
import br.com.leandro.starwarsapi.repository.PlanetaCrudRepository;
import br.com.leandro.starwarsapi.repository.PlanetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlanetaService {

    private PlanetaCrudRepository planetaCrudRepository;
    private PlanetRepository planetRepository;

    public PlanetaService(PlanetaCrudRepository planetaCrudRepository,
                          PlanetRepository planetRepository) {
        this.planetaCrudRepository = planetaCrudRepository;
        this.planetRepository = planetRepository;
    }

    public Planeta salvarPlaneta(Planeta planeta) {
        return planetaCrudRepository.save(planeta);
    }

    public void excluirPlaneta(String idPlaneta) {
        planetaCrudRepository.deleteById(idPlaneta);
    }

    public Optional<Planeta> encontrarPorId(String idPlaneta) {
        return planetaCrudRepository.findById(idPlaneta);
    }

    public Planeta editarPlaneta(Planeta planetaASerAtualizado) {
        return planetaCrudRepository.save(planetaASerAtualizado);
    }

    public Long totalPlanetasPorFiltro(PageRequest pageRequest, String nome) {
        return planetRepository.totalPlanetasPorFiltro(pageRequest, nome);
    }

    public List<Planeta> planetasPorFiltro(PageRequest pageRequest, String nome) {
        return planetRepository.planetasPorFiltro(pageRequest, nome);
    }

    public void validaPlanetaComNomeExistente(Planeta planeta, Boolean desconsiderarProprioPlaneta) {
        if(planetRepository.encontraPlanetaPorNome(planeta, desconsiderarProprioPlaneta)) {
            throw new PlanetaExistenteException();
        }
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
