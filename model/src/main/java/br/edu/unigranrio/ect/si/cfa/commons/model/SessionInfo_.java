package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-02-13T22:51:15.071-0200")
@StaticMetamodel(SessionInfo.class)
public class SessionInfo_ {
	public static volatile SingularAttribute<SessionInfo, String> locale;
	public static volatile SingularAttribute<SessionInfo, String> ipAddr;
	public static volatile SingularAttribute<SessionInfo, String> macAddr;
	public static volatile SingularAttribute<SessionInfo, String> userAgent;
	public static volatile SingularAttribute<SessionInfo, String> hostname;
	public static volatile SingularAttribute<SessionInfo, String> operationSystem;
}
