package br.edu.unigranrio.ect.si.cfa.web.message;

import javax.faces.application.FacesMessage;
import java.io.Serializable;

@FunctionalInterface
public interface WebMessage extends Serializable {

    void msg(FacesMessage.Severity status, String msg, String details, String idPage);

    default void info(String msg, String details, String idPage) {
        msg(FacesMessage.SEVERITY_INFO, msg, details, idPage);
    }

    default void info(String msg, String detail) {
        info(msg, detail, null);
    }

    default void info(String msg) {
        info(msg, null);
    }

    default void warn(String msg, String details, String idPage) {
        msg(FacesMessage.SEVERITY_WARN, msg, details, idPage);
    }

    default void warn(String msg, String detail) {
        warn(msg, detail, null);
    }

    default void warn(String msg) {
        warn(msg, null);
    }

    default void error(String msg, String details, String idPage) {
        msg(FacesMessage.SEVERITY_ERROR, msg, details, idPage);
    }

    default void error(String msg, String detail) {
        error(msg, detail, null);
    }

    default void error(String msg) {
        error(msg, null);
    }

    default void fatal(String msg, String details, String idPage) {
        msg(FacesMessage.SEVERITY_FATAL, msg, details, idPage);
    }

    default void fatal(String msg, String detail) {
        fatal(msg, detail, null);
    }

    default void fatal(String msg) {
        fatal(msg, null);
    }


}
