package br.edu.unigranrio.ect.si.cfa.web.message.bean;


import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.annotation.MessageResource;
import br.edu.unigranrio.ect.si.cfa.web.message.AuthMessage;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named("authMessage")
public class AuthMessageBean implements AuthMessage {

    private static final long serialVersionUID = -705388947939385985L;

    @Inject
    private Message message;

    @Inject @MessageResource
    private ResourceBundle bundle;

    @Override
    public void authMsg(FacesMessage.Severity severity, String msg, AuthException.Type type) {
        message.msg(severity, messageForType(type), null, null);
    }

    private String messageForType(AuthException.Type type) {
        switch (type) {
            case INVALID_PASSWORD:
                return bundle.getString("user.password.wrong");

            case LOGIN:
                return bundle.getString("user.session.login.error");

            case LOGOUT:
                return bundle.getString("user.session.logout.error");

            case USER_INACTIVE:
                return bundle.getString("user.inactive");

            case USER_NOT_FOUND:
                return bundle.getString("user.not.found");

            default:
                return bundle.getString("user.error");
        }
    }
}
