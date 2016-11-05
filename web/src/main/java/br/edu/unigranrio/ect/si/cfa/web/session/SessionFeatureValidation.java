package br.edu.unigranrio.ect.si.cfa.web.session;

import br.edu.unigranrio.ect.si.cfa.commons.model.Feature;
import br.edu.unigranrio.ect.si.cfa.service.FeatureService;
import br.edu.unigranrio.ect.si.cfa.web.annotation.LoggedUserId;
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

    @Inject
    private FeatureService service;

    @Inject @LoggedUserId
    private Long loggedUserId;

    private List<Feature> loggedUserFeatures;

    @PostConstruct
    public void init() {
        if (loggedUserFeatures == null || loggedUserFeatures.isEmpty())
            loggedUserFeatures = service.findFeaturesByUserId(loggedUserId);
    }

    @Override
    public boolean hasFeature(String featureName) {
        return isAuthenticate() &&
                loggedUserFeatures.stream()
                        .filter(f -> f.getName().equals(featureName))
                        .findAny().isPresent();
    }

    @Override
    public String urlFeature(String featureName) {
        if (isAuthenticate()) {
            Optional<Feature> optional = loggedUserFeatures.stream()
                    .filter(f -> f.getName().equals(featureName))
                    .findFirst();
            return optional.isPresent() ? Pages.actionList(optional.get().getUrl(), true) : Pages.actionMain();
        }
        return Pages.actionAuth(true);
    }

    private boolean isAuthenticate() {
        return loggedUserId != null;
    }
}
