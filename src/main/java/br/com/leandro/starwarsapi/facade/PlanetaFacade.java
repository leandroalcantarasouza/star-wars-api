package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.validator.JavaxValidator;
import br.com.leandro.starwarsapi.adapter.SwapiApiAdapter;
import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.PlanetaDtoMapper;
import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetaDto;
import br.com.leandro.starwarsapi.dto.PlanetaPayloadDto;
import br.com.leandro.starwarsapi.exception.PlanetaNaoEncontradoException;
import br.com.leandro.starwarsapi.service.PlanetaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class PlanetaFacade {

    private PlanetaDtoMapper planetaDtoMapper;
    private PlanetaService planetaService;
    private JavaxValidator<Planeta> planetJavaxValidator;
    private SwapiApiAdapter swapiApiAdapter;

    public PlanetaFacade(PlanetaDtoMapper planetaDtoMapper,
                         PlanetaService planetaService,
                         JavaxValidator<Planeta> planetJavaxValidator,
                         SwapiApiAdapter swapiApiAdapter) {
        this.planetaDtoMapper = planetaDtoMapper;
        this.planetaService = planetaService;
        this.planetJavaxValidator = planetJavaxValidator;
        this.swapiApiAdapter = swapiApiAdapter;
    }

    public PlanetaDto salvarPlaneta(PlanetaPayloadDto planetaPayloadDto) {
        Planeta planetaToBeSaved = planetaDtoMapper.from(planetaPayloadDto);
        planetJavaxValidator.validate(planetaToBeSaved);
        planetaService.validaPlanetaComNomeExistente(planetaToBeSaved, false);
        Long quantidadeAparicaoFilmes = swapiApiAdapter.recuperaAparicaoEmFilmesDe(planetaToBeSaved);
        planetaToBeSaved.setQuantidadeAparicaoEmFilmes(quantidadeAparicaoFilmes);
        Planeta savedPlaneta = planetaService.salvarPlaneta(planetaToBeSaved);
        return planetaDtoMapper.from(savedPlaneta);
    }

    public void excluirPlaneta(String idPlaneta) {
        planetaService.excluirPlaneta(idPlaneta);
    }

    public PlanetaDto editarPlaneta(PlanetaPayloadDto planetaPayloadDto, String idPlaneta) {
        Planeta planeta = planetaService.encontrarPorId(idPlaneta).orElseThrow(PlanetaNaoEncontradoException::new);
        Planeta planetaASerAtualizado = planetaDtoMapper.from(planetaPayloadDto, planeta);
        planetJavaxValidator.validate(planetaASerAtualizado);
        planetaService.validaPlanetaComNomeExistente(planetaASerAtualizado, true);
        Long quantidadeAparicaoFilmes = swapiApiAdapter.recuperaAparicaoEmFilmesDe(planetaASerAtualizado);
        planetaASerAtualizado.setQuantidadeAparicaoEmFilmes(quantidadeAparicaoFilmes);
        Planeta planetaEditado = planetaService.editarPlaneta(planetaASerAtualizado);
        return planetaDtoMapper.from(planetaEditado);
    }

    public Optional<PlanetaDto> encontrarPorId(String idPlaneta) {
        Optional<Planeta> planetaEncontado = planetaService.encontrarPorId(idPlaneta);
        return planetaEncontado.map(dto -> planetaDtoMapper.from(dto));
    }

    public Page<PlanetaDto> buscarPlaneta(BuscaPlanetaPayloadDto buscaPlaneta) {
        if(Objects.isNull(buscaPlaneta)) {
            buscaPlaneta = new BuscaPlanetaPayloadDto();
        } else {
            planetaService.validaNumeroPagina(buscaPlaneta);
            planetaService.validaQuantidadePorPagina(buscaPlaneta);
        }
        PageRequest pageRequest = PageRequest.of(buscaPlaneta.getNumeroPagina(), buscaPlaneta.getQuantidadePorPagina());
        Long totalPlanetasPorFiltro = planetaService.totalPlanetasPorFiltro(buscaPlaneta.getNome());
        List<Planeta> planetasPorFiltro = planetaService.planetasPorFiltro(pageRequest, buscaPlaneta.getNome());

        return new PageImpl<>(planetaDtoMapper.from(planetasPorFiltro), pageRequest, totalPlanetasPorFiltro);
    }
}
