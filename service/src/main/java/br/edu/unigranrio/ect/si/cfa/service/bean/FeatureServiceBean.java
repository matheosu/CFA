package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.*;
import br.edu.unigranrio.ect.si.cfa.service.FeatureService;

import javax.inject.Named;
import javax.persistence.criteria.*;
import java.util.List;

@Named
public class FeatureServiceBean extends ServiceBean implements FeatureService {

    private static final long serialVersionUID = 8890579990737820060L;

    @Override
    public Feature findFeatureByName(String name) {
        return singleResult(comparing(Feature.class, Feature_.name, name));
    }

    @Override
    public Feature findFeatureByURL(String url) {
        return singleResult(comparing(Feature.class, Feature_.url, url));
    }

    @Override
    public List<Feature> findFeaturesByUserId(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<Feature, User> util = createCriteriaUtil(Feature.class, User.class);
        CriteriaQuery<Feature> query = util.getQuery();
        Root<User> from = util.getFrom();
        Path<Long> pathUserId = from.get(User_.id);
        ListJoin<Role, Feature> join = from.join(User_.role).join(Role_.features);
        return resultList(query.select(join).where(cb.equal(pathUserId, userId)));
    }

}