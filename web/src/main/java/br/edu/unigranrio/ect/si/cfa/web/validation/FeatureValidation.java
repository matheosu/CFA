package br.edu.unigranrio.ect.si.cfa.web.validation;

import java.io.Serializable;

public interface FeatureValidation extends Serializable {

    boolean hasFeature(Long featureId);

    String urlFeature(final Long featureId);
}
