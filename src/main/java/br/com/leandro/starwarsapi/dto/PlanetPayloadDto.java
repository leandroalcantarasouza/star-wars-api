package br.com.leandro.starwarsapi.dto;

import java.io.Serializable;
import java.util.Objects;

public class PlanetPayloadDto implements Serializable {

    private static final long serialVersionUID = 7452454886742962716L;
    private String terreno;
    private String clima;
    private String nome;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetPayloadDto that = (PlanetPayloadDto) o;
        return Objects.equals(getTerreno(), that.getTerreno()) &&
                Objects.equals(getClima(), that.getClima()) &&
                Objects.equals(getNome(), that.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTerreno(), getClima(), getNome());
    }
}
