package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.Feature;

import java.util.List;

public interface FeatureService extends Service {

    Feature findFeatureByName(String name);

    Feature findFeatureByURL(String url);

    List<Feature> findFeaturesByUserId(Long userId);

}
