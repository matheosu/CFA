package br.edu.unigranrio.ect.si.cfa.web.exception;

import br.edu.unigranrio.ect.si.cfa.service.exception.ApplicationException;

public class WebApplicationException extends ApplicationException {

    private static final long serialVersionUID = 7372745231113055718L;

    public WebApplicationException() {
    }

    public WebApplicationException(String message) {
        super(message);
    }

    public WebApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebApplicationException(Throwable cause) {
        super(cause);
    }

    public WebApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
