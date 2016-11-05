package br.edu.unigranrio.ect.si.cfa.web.validation;

import java.io.Serializable;

public interface FeatureValidation extends Serializable {

    boolean hasFeature(String featureName);

    String urlFeature(final String featureName);
}
