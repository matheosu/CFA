package br.edu.unigranrio.ect.si.cfa.commons.search;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import java.util.List;

public interface SearchResult {

    Integer getPageSize();

    <T extends Entity<?>> List<T> getResult();

    default Integer getNumberOfPages() {
        return (getTotalResult() / getPageSize()) + (getTotalResult() % getPageSize() > 0 ? 1 : 0);
    }

    default Integer getTotalResult(){
        return getResult() != null ? getResult().size() : 0;
    }

}
