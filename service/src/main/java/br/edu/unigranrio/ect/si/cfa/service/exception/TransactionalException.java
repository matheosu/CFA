package br.edu.unigranrio.ect.si.cfa.service.exception;

public class TransactionalException extends ServiceRuntimeException {

    private static final long serialVersionUID = 5187491416599530791L;

    public TransactionalException() {
    }

    public TransactionalException(String message) {
        super(message);
    }

    public TransactionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalException(Throwable cause) {
        super(cause);
    }
}
