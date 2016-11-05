package br.edu.unigranrio.ect.si.cfa.web.action;

import br.edu.unigranrio.ect.si.cfa.commons.search.Search;
import br.edu.unigranrio.ect.si.cfa.commons.search.SearchResult;

public interface SearchAction {

    Search getSearch();

    SearchResult list();

}
