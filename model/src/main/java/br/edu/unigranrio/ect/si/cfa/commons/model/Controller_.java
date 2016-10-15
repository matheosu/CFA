package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Controller.class)
public class Controller_ extends BaseAuditable_ {

    public static volatile SingularAttribute<Controller, Long> id;
    public static volatile SingularAttribute<Controller, String> uuid;
    public static volatile SingularAttribute<Controller, String> model;

    public static volatile SingularAttribute<Controller, Localization> localization;
    public static volatile ListAttribute<Controller, Flow> flows;

}
