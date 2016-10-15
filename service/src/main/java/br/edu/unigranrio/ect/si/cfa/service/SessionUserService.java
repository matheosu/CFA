package br.edu.unigranrio.ect.si.cfa.service;


import br.edu.unigranrio.ect.si.cfa.commons.model.SessionUser;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;

import java.util.Calendar;
import java.util.List;

public interface SessionUserService extends Service {

    SessionUser findActiveSessionUser(User user);

    List<SessionUser> findSessionUserByLoginDate(User user, Calendar login);

    List<SessionUser> findSessionUserByLogoutDate(User user, Calendar logout);


}
