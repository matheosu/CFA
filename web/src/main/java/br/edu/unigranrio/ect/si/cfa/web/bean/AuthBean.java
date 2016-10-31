package br.edu.unigranrio.ect.si.cfa.web.bean;

import br.edu.unigranrio.ect.si.cfa.commons.factory.AuditListenerFactory;
import br.edu.unigranrio.ect.si.cfa.commons.model.Feature;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.service.AuthService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.Auth;
import br.edu.unigranrio.ect.si.cfa.web.message.AuthMessage;
import br.edu.unigranrio.ect.si.cfa.web.listener.WebAuditListener;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named("auth")
@SessionScoped
public class AuthBean implements Auth {

    private static final long serialVersionUID = 3015058683489706291L;

    @Inject AuthMessage message;
    @Inject AuthService authService;

    private User user;
    private List<Feature> features;

    private String email;
    private String password;

    @Override
    @Transactional
    public String doLogin() {
        try {
            user = authService.doLogin(email, password);
            features = user.getRole().getFeatures();
            if (!features.isEmpty())
                AuditListenerFactory.setEntityListener(new WebAuditListener(user.getId()));
            return Pages.actionMenu();
        } catch (AuthException e) {
            message.authMsg(e.getType());
        }
        return null;
    }

    @Override
    public String doLogout() {
        try {
            authService.doLogout(user);
        } catch (AuthException e) {
            message.authMsg(e.getType());
        }

        return null;
    }

    @Override
    public String getUserName() {
        return isAuthenticate() ? user.getName() : "";
    }

    @Override
    public boolean isAuthenticate() {
        return user != null;
    }

    @Override
    public boolean hasFeature(final String featureName) {
        if (isAuthenticate())
            return features.stream()
                        .filter(f -> f.getName().equals(featureName))
                        .findAny().isPresent();
        return false;
    }

    @Override
    public String urlFeature(final String featureName) {
        if (isAuthenticate()) {
            Optional<Feature> optional = features.stream()
                                .filter(f -> f.getName().equals(featureName))
                                .findFirst();
            return optional.isPresent() ? Pages.actionList(optional.get().getUrl(), true) : Pages.actionMenu();
        }
        return Pages.actionAuth(true);
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
