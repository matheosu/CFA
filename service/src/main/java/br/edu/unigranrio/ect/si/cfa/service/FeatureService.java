package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.Feature;

import java.util.List;
import java.util.Optional;

public interface FeatureService extends Service {

    Optional<Feature> findFeatureByName(String name);

    Optional<Feature> findFeatureByURL(String url);

    List<Feature> findFeaturesByUserId(Long userId);

}
