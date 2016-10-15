package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
public class Role_ extends BaseAuditable_ {
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, String> description;
	public static volatile ListAttribute<Role, User> users;
	public static volatile ListAttribute<Role, Feature> features;
}
