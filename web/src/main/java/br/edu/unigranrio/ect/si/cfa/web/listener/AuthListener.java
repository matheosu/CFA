package br.edu.unigranrio.ect.si.cfa.web.listener;

import br.edu.unigranrio.ect.si.cfa.web.Auth;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

public class AuthListener implements PhaseListener {

    private static final long serialVersionUID = -8035214378922845726L;

    @Inject Auth auth;
    @Inject FacesContext context;

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        if(!context.getViewRoot().getViewId().contains(Pages.ACTION_AUTH) ||
            !context.getViewRoot().getViewId().contains(Pages.ERROR_AUTH_EXPIRED)) {

            NavigationHandler nav = context.getApplication().getNavigationHandler();
            if (auth == null || auth.getUser() == null)
                nav.handleNavigation(context, null, Pages.ERROR_AUTH_EXPIRED);
        }
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
