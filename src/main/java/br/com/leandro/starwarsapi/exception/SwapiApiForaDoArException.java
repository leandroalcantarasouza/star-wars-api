package br.com.leandro.starwarsapi.exception;

public class SwapiApiForaDoArException extends RuntimeException {

    private String messageCode;

    public SwapiApiForaDoArException() {
        this.messageCode = "integracao.swapi.fora.do.ar.exception";
    }

    public String getMessageCode() {
        return messageCode;
    }
}
