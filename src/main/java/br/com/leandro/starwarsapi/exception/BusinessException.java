package br.com.leandro.starwarsapi.exception;

public class BusinessException extends RuntimeException {

    private String messageCode;
    private String[] messageArgs;

    public BusinessException(String messageCode) {
        this.messageCode = messageCode;
    }

    public BusinessException(String messageCode, String... messageArgs) {
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public String[] getMessageArgs() {
        return this.messageArgs;
    }
}
