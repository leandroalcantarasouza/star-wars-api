package br.com.leandro.starwarsapi.dto;

import java.io.Serializable;
import java.util.Objects;

public class BuscaPlanetaPayloadDto implements Serializable {

    private static final long serialVersionUID = 3271196102610886766L;

    private String nome;
    private Integer numeroPagina = 0;
    private Integer quantidadePorPagina = 10;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public Integer getQuantidadePorPagina() {
        return quantidadePorPagina;
    }

    public void setQuantidadePorPagina(Integer quantidadePorPagina) {
        this.quantidadePorPagina = quantidadePorPagina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuscaPlanetaPayloadDto that = (BuscaPlanetaPayloadDto) o;
        return Objects.equals(getNome(), that.getNome()) &&
                Objects.equals(getNumeroPagina(), that.getNumeroPagina()) &&
                Objects.equals(getQuantidadePorPagina(), that.getQuantidadePorPagina());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getNumeroPagina(), getQuantidadePorPagina());
    }
}
