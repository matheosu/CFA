package br.edu.unigranrio.ect.si.cfa.web.producer;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.web.annotation.LoggedRoleId;
import br.edu.unigranrio.ect.si.cfa.commons.annotation.LoggedUser;
import br.edu.unigranrio.ect.si.cfa.commons.annotation.LoggedUserId;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Objects;

public class LoggedUserProducer implements Serializable {

    private static final long serialVersionUID = -8058360850350664621L;

    @Inject
    HttpSession session;
    @Inject
    UserService userService;

    @Produces
    @LoggedUserId
    public Long getLoggedUserId() {
        Object object = session.getAttribute(Constants.LOGGED_USER_ATTR);
        return object != null ? Long.valueOf(object.toString()) : Constants.USER_ADMIN_ID;
    }

    @Produces
    @LoggedUser
    public User getLoggedUser(@LoggedUserId Long loggedUserId) {
        return !Objects.equals(loggedUserId, Constants.USER_ADMIN_ID) ? userService.find(User.class, loggedUserId) : null;
    }

    @Produces
    @LoggedRoleId
    public Long getLoggedRoleId(@LoggedUser User loggedUser) {
        return loggedUser != null ? loggedUser.getRole().getId() : Constants.ROLE_ADMIN_ID;
    }
}
