package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.FlowRestriction;
import br.edu.unigranrio.ect.si.cfa.model.FlowRestriction_;
import br.edu.unigranrio.ect.si.cfa.model.Period;
import br.edu.unigranrio.ect.si.cfa.model.Restriction;
import br.edu.unigranrio.ect.si.cfa.service.FlowRestrictionService;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Named
public class FlowRestrictionServiceBean extends ServiceBean implements FlowRestrictionService {

    private static final long serialVersionUID = -6704476718601361139L;

    @Override
    public FlowRestriction findByName(String name) {
        return singleResult(comparing(FlowRestriction.class, FlowRestriction_.name, name));
    }

    @Override
    public List<FlowRestriction> findByRestriction(Restriction restriction) {
        return resultList(comparing(FlowRestriction.class, FlowRestriction_.restriction, restriction));
    }

    @Override
    public List<FlowRestriction> findByPeriod(Period period) {
        return resultList(comparing(FlowRestriction.class, FlowRestriction_.period, period));
    }

    @Override
    public List<FlowRestriction> findByRestrictionAndPeriod(Restriction restriction, Period period) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<FlowRestriction, FlowRestriction> util = createCriteriaUtil(FlowRestriction.class);
        CriteriaQuery<FlowRestriction> query = util.getQuery();
        Root<FlowRestriction> from = util.getFrom();
        query = query.where(cb.and(cb.equal(from.get(FlowRestriction_.restriction), restriction),
                cb.equal(from.get(FlowRestriction_.period), period)));
        return resultList(query);
    }
}
