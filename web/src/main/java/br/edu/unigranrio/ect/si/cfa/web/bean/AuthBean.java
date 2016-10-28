package br.edu.unigranrio.ect.si.cfa.web.bean;

import br.edu.unigranrio.ect.si.cfa.commons.listener.AuditListenerFactory;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.service.AuthService;
import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.Auth;
import br.edu.unigranrio.ect.si.cfa.web.Message;
import br.edu.unigranrio.ect.si.cfa.web.listener.WebAuditListener;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("auth")
@SessionScoped
public class AuthBean implements Auth {

    private static final long serialVersionUID = 3015058683489706291L;

    @Inject Message message;
    @Inject AuthService authService;

    private User user;
    private String email;
    private String password;

    @Override
    public String doLogin() {
        try {
            user = authService.doLogin(email, password);

            AuditListenerFactory.addEntityListener(new WebAuditListener(user.getId()));

            return Pages.actionMenu();
        } catch (AuthException e) {
            message.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String doLogout() {
        try {
            authService.doLogout(user);
        } catch (AuthException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
