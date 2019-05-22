package br.com.leandro.starwarsapi.exception;

public class PlanetaExistenteException extends BusinessException {

    public PlanetaExistenteException() {
        super("planeta.existente.exception");
    }
}
