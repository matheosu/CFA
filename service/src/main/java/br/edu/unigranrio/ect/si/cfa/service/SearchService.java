package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.search.Search;
import br.edu.unigranrio.ect.si.cfa.commons.search.SearchResult;

public interface SearchService extends Service {

    SearchResult search(Search search);

}
