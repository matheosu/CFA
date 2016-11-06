package br.edu.unigranrio.ect.si.cfa.web.listener;

import br.edu.unigranrio.ect.si.cfa.web.authentication.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import static br.edu.unigranrio.ect.si.cfa.web.util.Pages.*;

public class AuthenticatorListener implements PhaseListener {

    private static final long serialVersionUID = -8035214378922845726L;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticatorListener.class);

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        final FacesContext context = phaseEvent.getFacesContext();
        if(!context.getViewRoot().getViewId().equals(INDEX.replace(URL_PATTERN, XHTML)) &&
           !context.getViewRoot().getViewId().equals(ACTION_AUTH.replace(URL_PATTERN, XHTML)) &&
           !context.getViewRoot().getViewId().equals(ERROR_AUTH_EXPIRED.replace(URL_PATTERN, XHTML))) {

            Authenticator auth = context.getApplication().evaluateExpressionGet(context, "#{auth}", Authenticator.class);
            NavigationHandler nav = context.getApplication().getNavigationHandler();
            if (auth == null || !auth.isAuthenticate()) {
                logger.warn("Unauthorized ViewRoot {};", context.getViewRoot().getViewId());
                nav.handleNavigation(context, null, ERROR_AUTH_EXPIRED);
            }
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
