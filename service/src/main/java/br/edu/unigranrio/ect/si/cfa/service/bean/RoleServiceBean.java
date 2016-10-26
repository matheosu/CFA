package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.Feature;
import br.edu.unigranrio.ect.si.cfa.model.Role;
import br.edu.unigranrio.ect.si.cfa.model.Role_;
import br.edu.unigranrio.ect.si.cfa.service.RoleService;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.List;

@Named
public class RoleServiceBean extends BaseService implements RoleService {

    @Override
    public List<Role> findRolesByName(String name) {
        return resultList(comparing(Role.class, Role_.name, name));
    }

    @Override
    public List<Role> findRolesByFeatures(List<Feature> features) {
        CriteriaUtil<Role, Role> util = createCriteriaUtil(Role.class);
        CriteriaQuery<Role> query = util.getQuery();
        Root<Role> from = util.getFrom();
        ListJoin<Role, Feature> join = from.join(Role_.features);

        return resultList(query.where(join.in(features)));
    }
}
