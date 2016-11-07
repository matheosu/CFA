package br.edu.unigranrio.ect.si.cfa.commons.search;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import java.util.List;
import java.util.Set;

public interface Search {

    <T extends Entity<?>> Class<T> getEntity();

    List<Criteria> getCriterias();

    Pagination getPagination();

    Set<Order> getOrders();

}
