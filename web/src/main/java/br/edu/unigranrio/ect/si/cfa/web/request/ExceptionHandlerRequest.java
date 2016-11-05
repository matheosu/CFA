package br.edu.unigranrio.ect.si.cfa.web.request;

import br.edu.unigranrio.ect.si.cfa.web.handler.ExceptionHandler;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("exceptionHandler")
public class ExceptionHandlerRequest implements ExceptionHandler {

    private static final String ERROR_KEY = "javax.servlet.error.";
    private static final String MESSAGE_KEY = ERROR_KEY + "message";
    private static final String EXCEPTION_KEY = ERROR_KEY + "exception";
    private static final String STATUS_CODE_KEY = ERROR_KEY + "status_code";
    private static final String REQUEST_URI_KEY = ERROR_KEY + "request_uri";
    private static final String SERVLET_NAME_KEY = ERROR_KEY + "servlet_name";
    private static final String EXCEPTION_TYPE_KEY = ERROR_KEY + "exception_type";

    @Inject
    ExternalContext externalContext;


    @Override
    public Integer getStatusCode() {
        return getObjectFromRequest(STATUS_CODE_KEY);
    }

    @Override
    public String getMessage() {
        return getStringFromRequest(MESSAGE_KEY);
    }

    @Override
    public Exception getException() {
        return getObjectFromRequest(EXCEPTION_KEY);
    }

    @Override
    public String getExceptionType() {
        return getStringFromRequest(EXCEPTION_TYPE_KEY);
    }

    @Override
    public String getRequestURI() {
        return getStringFromRequest(REQUEST_URI_KEY);
    }

    @Override
    public String getServletName() {
        return getStringFromRequest(SERVLET_NAME_KEY);
    }

    @SuppressWarnings("unchecked")
    private <T> T getObjectFromRequest(String key) {
        return (T) externalContext.getRequestMap().get(key);
    }

    private String getStringFromRequest(String key) {
        return String.valueOf((Object) getObjectFromRequest(key));
    }
}
