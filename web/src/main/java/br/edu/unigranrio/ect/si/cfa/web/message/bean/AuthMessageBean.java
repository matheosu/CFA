package br.edu.unigranrio.ect.si.cfa.web.message.bean;

import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.annotation.MessageResource;
import br.edu.unigranrio.ect.si.cfa.web.message.AuthMessage;
import br.edu.unigranrio.ect.si.cfa.web.message.WebMessage;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named("authMessage")
public class AuthMessageBean implements AuthMessage {

    private static final long serialVersionUID = -705388947939385985L;

    private final WebMessage webMessage;
    private final ResourceBundle bundle;

    @Inject
    public AuthMessageBean(WebMessage webMessage, @MessageResource ResourceBundle bundle) {
        this.webMessage = webMessage;
        this.bundle = bundle;
    }

    @Override
    public void authMsg(FacesMessage.Severity severity, String msg, AuthException.Type type) {
        webMessage.msg(severity, messageForType(type), null, null);
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
