package br.edu.unigranrio.ect.si.cfa.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Calendar;

@StaticMetamodel(Flow.class)
public class Flow_ extends BaseAuditable_ {

    public static volatile SingularAttribute<Flow, Long> id;
    public static volatile SingularAttribute<Flow, Float> measure;
    public static volatile SingularAttribute<Flow, Calendar> registrantion;

    public static volatile SingularAttribute<Flow, User> user;
    public static volatile SingularAttribute<Flow, Controller> controller;


}
