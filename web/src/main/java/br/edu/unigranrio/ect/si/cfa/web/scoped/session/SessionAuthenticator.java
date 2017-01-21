package br.edu.unigranrio.ect.si.cfa.web.scoped.session;

import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.AuthService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.authentication.Authenticator;
import br.edu.unigranrio.ect.si.cfa.web.authentication.PrincipalWrapper;
import br.edu.unigranrio.ect.si.cfa.web.message.AuthMessage;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;

@Named("auth")
@SessionScoped
public class SessionAuthenticator implements Authenticator {

    private static final long serialVersionUID = 3015058683489706291L;

    private final AuthMessage message;
    private final AuthService authService;


    private User user;
    private Principal principal;

    private String email;
    private String password;

    @Inject
    public SessionAuthenticator(AuthMessage message, AuthService authService) {
        this.message = message;
        this.authService = authService;
    }

    @Override
    @Transactional
    public String doLogin() {
        try {
            user = authService.doLogin(email, password);
            return Pages.actionMain();
        } catch (AuthException e) {
            message.authMsg(e.getType());
        }
        return "";
    }

    @Override
    @Transactional
    public String doLogout() {
        try {
            authService.doLogout(user);
            return Pages.actionAuth();
        } catch (AuthException e) {
            message.authMsg(e.getType());
        }
        return "";
    }

    @Override
    public Principal getPrincipal() {
        if (isAuthenticate()) {
            if (principal == null)
                principal = new PrincipalWrapper(user.getName());
            return principal;
        }
        return null;
    }

    @Override
    public boolean isAuthenticate() {
        return user != null;
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


}
