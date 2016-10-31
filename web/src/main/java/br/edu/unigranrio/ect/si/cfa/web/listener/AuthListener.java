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

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        final FacesContext context = phaseEvent.getFacesContext();
        if(!context.getViewRoot().getViewId().equals(Pages.INDEX) &&
                 !context.getViewRoot().getViewId().equals(Pages.ACTION_AUTH) &&
                 !context.getViewRoot().getViewId().equals(Pages.ERROR_AUTH_EXPIRED)) {

            Auth auth = context.getApplication().evaluateExpressionGet(context, "#{auth}", Auth.class);
            NavigationHandler nav = context.getApplication().getNavigationHandler();
            if (auth == null || !auth.isAuthenticate())
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
