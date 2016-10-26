package br.edu.unigranrio.ect.si.cfa.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FlowRestriction.class)
public class FlowRestriction_ extends BaseAuditable_ {

    public static volatile SingularAttribute<FlowRestriction, Long> id;
    public static volatile SingularAttribute<FlowRestriction, String> name;
    public static volatile SingularAttribute<FlowRestriction, String> description;
    public static volatile SingularAttribute<FlowRestriction, Period> period;
    public static volatile SingularAttribute<FlowRestriction, Restriction> restriction;
    public static volatile SingularAttribute<FlowRestriction, Float> value;

    public static volatile ListAttribute<FlowRestriction, User> users;

}
