package br.edu.unigranrio.ect.si.cfa.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-15T13:17:36.810-0300")
@StaticMetamodel(SessionUser.class)
public class SessionUser_ extends BaseEntity_ {
	public static volatile SingularAttribute<SessionUser, Long> id;
	public static volatile SingularAttribute<SessionUser, Calendar> loginDate;
	public static volatile SingularAttribute<SessionUser, Calendar> logoutDate;
	public static volatile SingularAttribute<SessionUser, SessionInfo> sessionInfo;
	public static volatile SingularAttribute<SessionUser, User> user;
}
