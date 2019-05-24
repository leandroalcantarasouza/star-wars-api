package br.com.leandro.starwarsapi.facade;

import br.com.leandro.starwarsapi.JavaxValidator;
import br.com.leandro.starwarsapi.domain.EditDtoMapper;
import br.com.leandro.starwarsapi.domain.Planeta;
import br.com.leandro.starwarsapi.domain.SaveDtoMapper;
import br.com.leandro.starwarsapi.dto.BuscaPlanetaPayloadDto;
import br.com.leandro.starwarsapi.dto.PlanetDto;
import br.com.leandro.starwarsapi.dto.PlanetPayloadDto;
import br.com.leandro.starwarsapi.service.PlanetService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Component
public class PlanetFacade {

    private SaveDtoMapper saveDtoMapper;
    private EditDtoMapper editDtoMapper;
    private PlanetService planetService;
    private JavaxValidator<Planeta> planetJavaxValidator;

    public PlanetFacade(SaveDtoMapper saveDtoMapper,
                        EditDtoMapper editDtoMapper,
                        PlanetService planetService,
                        JavaxValidator<Planeta> planetJavaxValidator) {
        this.saveDtoMapper = saveDtoMapper;
        this.editDtoMapper = editDtoMapper;
        this.planetService = planetService;
        this.planetJavaxValidator = planetJavaxValidator;
    }

    public PlanetDto savePlanet(PlanetPayloadDto planetPayloadDto) {
        Planeta planetaToBeSaved = saveDtoMapper.from(planetPayloadDto);
        planetJavaxValidator.validate(planetaToBeSaved);
        planetService.validaPlanetaComNomeExistenteInsercao(planetaToBeSaved);
        Planeta savedPlaneta = planetService.savePlanet(planetaToBeSaved);
        return saveDtoMapper.from(savedPlaneta);
    }

    public void excluirPlaneta(String idPlaneta) {
        planetService.excluirPlaneta(idPlaneta);
    }

    public PlanetDto editarPlaneta(PlanetPayloadDto planetaPayloadDto, String idPlaneta) {
        Planeta planeta = planetService.descobrirPorId(idPlaneta);
        Planeta planetaASerAtualizado = editDtoMapper.from(planetaPayloadDto, planeta);
        planetJavaxValidator.validate(planetaASerAtualizado);
        planetService.validaPlanetaComNomeExistenteEdicao(planetaASerAtualizado);
        Planeta planetaEditado = planetService.editarPlaneta(planetaASerAtualizado);
        return saveDtoMapper.from(planetaEditado);
    }

    public Optional<PlanetDto> encontrarPorId(String idPlaneta) {
        Optional<Planeta> planetaEncontado = planetService.encontrarPorId(idPlaneta);
        return planetaEncontado.map(dto -> saveDtoMapper.from(dto));
    }

    public Slice<PlanetDto> buscarPlaneta(BuscaPlanetaPayloadDto buscaPlaneta) {
        if(Objects.isNull(buscaPlaneta)) {
            buscaPlaneta = new BuscaPlanetaPayloadDto();
        } else {
            planetService.validaNumeroPagina(buscaPlaneta);
            planetService.validaQuantidadePorPagina(buscaPlaneta);
        }
        PageRequest pageRequest = PageRequest.of(buscaPlaneta.getNumeroPagina(), buscaPlaneta.getQuantidadePorPagina());
        Long totalPlanetasPorFiltro = planetService.totalPlanetasPorFiltro(pageRequest, buscaPlaneta.getNome());
        List<Planeta> planetasPorFiltro = planetService.planetasPorFiltro(pageRequest, buscaPlaneta.getNome());

        return new PageImpl<>(saveDtoMapper.from(planetasPorFiltro), pageRequest, totalPlanetasPorFiltro);
    }
}
