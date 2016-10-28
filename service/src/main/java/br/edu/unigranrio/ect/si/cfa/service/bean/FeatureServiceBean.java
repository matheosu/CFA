package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.Feature;
import br.edu.unigranrio.ect.si.cfa.commons.model.Feature_;
import br.edu.unigranrio.ect.si.cfa.service.FeatureService;

import javax.inject.Named;

@Named
public class FeatureServiceBean extends BaseService implements FeatureService {

    private static final long serialVersionUID = 8890579990737820060L;

    @Override
    public Feature findFeatureByName(String name) {
        return singleResult(comparing(Feature.class, Feature_.name, name));
    }

    @Override
    public Feature findFeatureByURL(String url) {
        return singleResult(comparing(Feature.class, Feature_.url, url));
    }
}