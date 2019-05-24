package br.com.leandro.starwarsapi.dto;

import java.io.Serializable;
import java.util.Objects;

public class PlanetDto implements Serializable {


    private static final long serialVersionUID = -7702703807743516298L;

    private String id;
    private String terreno;
    private String clima;
    private String nome;
    private Long quantidadeAparicaoFilmes;

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantidadeAparicaoFilmes() {
        return quantidadeAparicaoFilmes;
    }

    public void setQuantidadeAparicaoFilmes(Long quantidadeAparicaoFilmes) {
        this.quantidadeAparicaoFilmes = quantidadeAparicaoFilmes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetDto planetDto = (PlanetDto) o;
        return getId().equals(planetDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
