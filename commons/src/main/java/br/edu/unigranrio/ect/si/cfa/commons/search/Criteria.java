package br.edu.unigranrio.ect.si.cfa.commons.search;

public interface Criteria extends Attribute {

    CriteriaType getType();

    <T extends Comparable<T>> T getValue();

    enum CriteriaType {
        EQUAL, LESS, GREATER, LESS_EQUAL, GREATER_EQUAL, NOT_EQUAL
    }
}
