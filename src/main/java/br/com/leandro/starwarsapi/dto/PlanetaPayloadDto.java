package br.com.leandro.starwarsapi.dto;

import java.io.Serializable;
import java.util.Objects;

public class PlanetaPayloadDto implements Serializable {

    private static final long serialVersionUID = 6166296831171526987L;

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
        PlanetaPayloadDto that = (PlanetaPayloadDto) o;
        return Objects.equals(getTerreno(), that.getTerreno()) &&
                Objects.equals(getClima(), that.getClima()) &&
                Objects.equals(getNome(), that.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTerreno(), getClima(), getNome());
    }
}
