package br.edu.unigranrio.ect.si.cfa.web.producer;

import br.edu.unigranrio.ect.si.cfa.web.annotation.LoggedUserId;
import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

public class LoggedUserProducer implements Serializable {

    private static final long serialVersionUID = -8058360850350664621L;

    @Inject HttpSession session;

    @Produces @LoggedUserId
    public Long getLoggedUserId() {
        Object object = session.getAttribute(Constants.LOGGED_USER_ATTR);
        return object != null ? Long.valueOf(object.toString()) : Constants.SYSTEM_ID;
    }
}
