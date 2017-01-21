package br.edu.unigranrio.ect.si.cfa.web.scoped.session;

import br.edu.unigranrio.ect.si.cfa.model.Feature;
import br.edu.unigranrio.ect.si.cfa.service.FeatureService;
import br.edu.unigranrio.ect.si.cfa.commons.annotation.LoggedUserId;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;
import br.edu.unigranrio.ect.si.cfa.web.validation.FeatureValidation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@SessionScoped
@Named("featureValidation")
public class SessionFeatureValidation implements FeatureValidation {

    private static final long serialVersionUID = -6491142171002448510L;

    private final Long loggedUserId;
    private final FeatureService service;

    private List<Feature> loggedUserFeatures;

    @Inject
    public SessionFeatureValidation(FeatureService service, @LoggedUserId Long loggedUserId) {
        this.service = service;
        this.loggedUserId = loggedUserId;
    }

    @PostConstruct
    public void init() {
        if (loggedUserFeatures == null || loggedUserFeatures.isEmpty())
            loggedUserFeatures = service.findFeaturesByUserId(loggedUserId);
    }

    @Override
    public boolean hasFeature(Long featureId) {
        return isAuthenticate() &&
                loggedUserFeatures.stream()
                        .anyMatch(f -> f.getId().equals(featureId));
    }

    @Override
    public String urlFeature(Long featureId) {
        if (isAuthenticate()) {
            Optional<Feature> optional = loggedUserFeatures.stream()
                    .filter(f -> f.getId().equals(featureId))
                    .findFirst();
            return optional.isPresent() ? Pages.actionList(optional.get().getUrl(), true) : Pages.actionMain();
        }
        return Pages.actionAuth(true);
    }

    private boolean isAuthenticate() {
        return loggedUserId != null;
    }
}
