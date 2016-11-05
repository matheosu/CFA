package br.edu.unigranrio.ect.si.cfa.web.session;

import br.edu.unigranrio.ect.si.cfa.commons.factory.AuditListenerFactory;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.service.AuthService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.Authenticator;
import br.edu.unigranrio.ect.si.cfa.web.listener.WebAuditListener;
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

    @Inject
    AuthMessage message;
    @Inject
    AuthService authService;

    private User user;
    private Principal principal;

    private String email;
    private String password;

    @Override
    @Transactional
    public String doLogin() {
        try {
            user = authService.doLogin(email, password);
            if (user != null)
                AuditListenerFactory.setEntityListener(new WebAuditListener(user.getId()));
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
                principal = PrincipalWrapper.wrapper(user.getName());
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

    /**
     * Principal Wrapper
     */
    private static final class PrincipalWrapper implements Principal {

        private final String name;

        private PrincipalWrapper(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        static Principal wrapper(String name) {
            return new PrincipalWrapper(name);
        }

    }

}
