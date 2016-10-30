package br.edu.unigranrio.ect.si.cfa.web.message;

import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;

import javax.faces.application.FacesMessage;
import java.io.Serializable;

@FunctionalInterface
public interface AuthMessage extends Serializable {

    void authMsg(FacesMessage.Severity severity, String msg, AuthException.Type type);

    default void authMsg(String msg, AuthException.Type type) {
        switch (type) {
            case USER_INACTIVE:
            case USER_NOT_FOUND:
            case INVALID_PASSWORD:
                authMsg(FacesMessage.SEVERITY_WARN, msg, type);
                break;

            case LOGIN:
            case LOGOUT:
                authMsg(FacesMessage.SEVERITY_ERROR, msg, type);
                break;
        }
    }

    default void authMsg(AuthException.Type type) {
        authMsg(null, type);
    }
}
