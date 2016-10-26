package br.edu.unigranrio.ect.si.cfa.commons.model;

import java.util.Calendar;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BaseAuditable.class)
public class BaseAuditable_ extends BaseEntity_ {
    public static volatile SingularAttribute<BaseAuditable<?>, User> userCreated;
    public static volatile SingularAttribute<BaseAuditable<?>, Long> userCreatedId;
    public static volatile SingularAttribute<BaseAuditable<?>, Calendar> dateCreated;
    public static volatile SingularAttribute<BaseAuditable<?>, User> userUpdated;
    public static volatile SingularAttribute<BaseAuditable<?>, Long> userUpdatedId;
    public static volatile SingularAttribute<BaseAuditable<?>, Calendar> dateUpdated;
    public static volatile SingularAttribute<BaseAuditable<?>, Integer> version;

}
