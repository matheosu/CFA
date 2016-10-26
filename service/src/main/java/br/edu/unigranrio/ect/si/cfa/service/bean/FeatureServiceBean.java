package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.Feature;
import br.edu.unigranrio.ect.si.cfa.model.Feature_;
import br.edu.unigranrio.ect.si.cfa.service.FeatureService;

import javax.inject.Named;

@Named
public class FeatureServiceBean extends BaseService implements FeatureService {

    @Override
    public Feature findFeatureByName(String name) {
        return singleResult(comparing(Feature.class, Feature_.name, name));
    }

    @Override
    public Feature findFeatureByURL(String url) {
        return singleResult(comparing(Feature.class, Feature_.url, url));
    }
}