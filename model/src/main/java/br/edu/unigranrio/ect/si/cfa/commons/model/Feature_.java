package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Feature.class)
public class Feature_ extends BaseAuditable_ {

    public static volatile SingularAttribute<Feature, Long> id;
    public static volatile SingularAttribute<Feature, String> name;
    public static volatile SingularAttribute<Feature, String> description;
    public static volatile SingularAttribute<Feature, String> url;

    public static volatile SingularAttribute<Feature, Feature> dependency;

    public static volatile ListAttribute<Feature, Role> roles;
    public static volatile ListAttribute<Feature, Feature> dependents;

}