package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.search.Criteria;
import br.edu.unigranrio.ect.si.cfa.commons.search.Search;
import br.edu.unigranrio.ect.si.cfa.commons.search.SearchResult;
import br.edu.unigranrio.ect.si.cfa.model.Entity;
import br.edu.unigranrio.ect.si.cfa.service.SearchService;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

public class SearchServiceBean extends ServiceBean implements SearchService {

    private static final long serialVersionUID = 8418459550627259315L;

    @Override
    public SearchResult search(Search search) {
        CriteriaUtil<Entity<?>, Entity<?>> util = createCriteriaUtil(search.getEntity());

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final  Root<Entity<?>> from = util.getFrom();
        CriteriaQuery<Entity<?>> query = util.getQuery();

        Optional<Predicate> predicate = search.getCriterias()
                .stream()
                .map(c -> predicateCriteria(cb, from, c))
                .map(cb::and)
                .findFirst();
        if (predicate.isPresent())
            query = query.where(predicate.get());

        final List<Entity<?>> result = resultList(query);
        return new SearchResult() {
            @Override
            public Integer getPageSize() {
                return search.getPagination().size();
            }

            @Override
            public <T extends Entity<?>> List<T> getResult() {
                return (List<T>) result;
            }
        };
    }

    private <T extends Entity<?>> Predicate predicateCriteria(CriteriaBuilder cb, Root<T> from, Criteria criteria) {
        Object value = criteria.getValue();
        Path<Object> path = from.get(criteria.getAttribute());
        switch (criteria.getType()) {
            case EQUAL:
                return cb.equal(path, value);
            case NOT_EQUAL:
                return cb.notEqual(path, value);
        }
        return null;
    }
}
