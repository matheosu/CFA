package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Localization.class)
public class Localization_ extends BaseAuditable_ {

    public static volatile SingularAttribute<Localization, Long> id;
    public static volatile SingularAttribute<Localization, String> name;
    public static volatile SingularAttribute<Localization, String> description;

    public static volatile ListAttribute<Localization, Controller> controllers;

}