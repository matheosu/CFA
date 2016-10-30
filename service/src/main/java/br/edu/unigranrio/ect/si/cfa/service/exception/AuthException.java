package br.edu.unigranrio.ect.si.cfa.service.exception;

public class AuthException extends ServiceExeption {

    private static final long serialVersionUID = 6014990833151318131L;

    public enum Type {
        USER_INACTIVE,
        USER_NOT_FOUND,
        INVALID_PASSWORD,
        LOGIN,
        LOGOUT,
        ;
    }

    private Type type;

    public AuthException(Type type) {
        this.type = type;
    }

    public AuthException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public AuthException(String message, Throwable cause, Type type) {
        super(message, cause);
        this.type = type;
    }

    public AuthException(Throwable cause, Type type) {
        super(cause);
        this.type = type;
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Type type) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
