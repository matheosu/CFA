package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ extends BaseEntity_ {
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Boolean> active;

	public static volatile SingularAttribute<User, Role> role;
	public static volatile SingularAttribute<User, FlowRestriction> flowRestriction;

	public static volatile ListAttribute<User, SessionUser> sessions;
	public static volatile ListAttribute<User, Flow> flows;
}
