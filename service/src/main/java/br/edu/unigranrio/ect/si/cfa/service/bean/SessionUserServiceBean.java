package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.SessionUser;
import br.edu.unigranrio.ect.si.cfa.commons.model.SessionUser_;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.service.SessionUserService;

import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.List;

@Named
public class SessionUserServiceBean extends BaseService implements SessionUserService {

    @Override
    public SessionUser findActiveSessionUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<SessionUser, SessionUser> util = createCriteriaUtil(SessionUser.class);

        CriteriaQuery<SessionUser> query = util.getQuery();
        Root<SessionUser> from = util.getFrom();

        Join<SessionUser, User> joinUser = from.join(SessionUser_.user);
        Path<Calendar> login = from.get(SessionUser_.loginDate);
        Path<Calendar> logout = from.get(SessionUser_.logoutDate);

        return singleResult(
                em.createQuery(
                        query.where(cb.equal(joinUser, user), logout.isNull())
                             .orderBy(cb.asc(login))
                )
        );
    }

    @Override
    public List<SessionUser> findSessionUserByLoginDate(User user, Calendar login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<SessionUser, SessionUser> util = createCriteriaUtil(SessionUser.class);

        CriteriaQuery<SessionUser> query = util.getQuery();
        Root<SessionUser> from = util.getFrom();

        Join<SessionUser, User> joinUser = from.join(SessionUser_.user);
        Path<Calendar> pathLogin = from.get(SessionUser_.loginDate);

        return resultList(
                em.createQuery(
                        query.where(cb.equal(joinUser, user), cb.equal(pathLogin, login))
                                .orderBy(cb.asc(pathLogin))
                )
        );
    }

    @Override
    public List<SessionUser> findSessionUserByLogoutDate(User user, Calendar logout) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<SessionUser, SessionUser> util = createCriteriaUtil(SessionUser.class);

        CriteriaQuery<SessionUser> query = util.getQuery();
        Root<SessionUser> from = util.getFrom();

        Join<SessionUser, User> joinUser = from.join(SessionUser_.user);
        Path<Calendar> pathLogout = from.get(SessionUser_.logoutDate);

        return resultList(
                em.createQuery(
                        query.where(cb.equal(joinUser, user), cb.equal(pathLogout, logout))
                                .orderBy(cb.asc(pathLogout))
                )
        );
    }
}
