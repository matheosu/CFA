package br.edu.unigranrio.ect.si.cfa.web.handler;

public interface ExceptionHandler {

    Integer getStatusCode();

    String getMessage();

    Exception getException();

    String getExceptionType();

    String getRequestURI();

    String getServletName();

}
