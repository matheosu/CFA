package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.Role;
import br.edu.unigranrio.ect.si.cfa.commons.model.User;
import br.edu.unigranrio.ect.si.cfa.commons.model.User_;
import br.edu.unigranrio.ect.si.cfa.service.UserService;

import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.List;

@Named
public class UserServiceBean extends BaseService implements UserService {

    private static final long serialVersionUID = 6581130279830770035L;

    @Override
    public User findUserByEmail(String email) {
        return singleResult(comparing(User.class, User_.email, email));
    }

    @Override
    public List<User> findUsersLikeByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUtil<User, User> util = createCriteriaUtil(User.class);
        CriteriaQuery<User> query = util.getQuery();
        Root<User> from = util.getFrom();

        return resultList(query.where(
                cb.like(from.get(User_.name), name))
        );
    }

    @Override
    public List<User> findUserByActivity(Boolean activity) {
        return resultList(comparing(User.class, User_.active, activity));
    }

    @Override
    public List<User> findUsersByRole(Role role) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUtil<User, User> util = createCriteriaUtil(User.class);
        CriteriaQuery<User> query = util.getQuery();
        Join<User, Role> join = util.getFrom().join(User_.role);

        return resultList(query.where(cb.equal(join, role)));
    }

}
