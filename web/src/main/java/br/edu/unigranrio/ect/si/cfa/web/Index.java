package br.edu.unigranrio.ect.si.cfa.web;

import br.edu.unigranrio.ect.si.cfa.web.authentication.Authenticator;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class Index implements Serializable {

    private static final long serialVersionUID = -27976548695925185L;

    private final Authenticator auth;

    @Inject
    public Index(Authenticator auth) {
        this.auth = auth;
    }

    public void init(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        NavigationHandler navigation = context.getApplication().getNavigationHandler();

        if (auth == null || !auth.isAuthenticate()) {
            navigation.handleNavigation(context, null, Pages.actionAuth());
        } else {
            navigation.handleNavigation(context, null, Pages.actionMain());
        }
    }

}
