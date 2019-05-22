package br.com.leandro.starwarsapi.exception;

public class PlanetaNaoEncontradoException extends BusinessException{

    public PlanetaNaoEncontradoException() {
        super("planeta.nao.encontrado.exception");
    }
}
