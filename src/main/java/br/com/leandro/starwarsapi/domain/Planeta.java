package br.com.leandro.starwarsapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Document("planet")
public class Planeta {

    @Id
    private String id;
    @NotBlank
    private String terreno;
    @NotBlank
    private String clima;
    @NotBlank
    private String nome;
    private Integer quantidadeAparicaoEmFilmes;
    @Version
    private Long versao;

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

    public Integer getQuantidadeAparicaoEmFilmes() {
        return quantidadeAparicaoEmFilmes;
    }

    public void setQuantidadeAparicaoEmFilmes(Integer quantidadeAparicaoEmFilmes) {
        this.quantidadeAparicaoEmFilmes = quantidadeAparicaoEmFilmes;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planeta planeta = (Planeta) o;
        return Objects.equals(getId(), planeta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
