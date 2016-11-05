package br.edu.unigranrio.ect.si.cfa.commons.search;

public interface Order extends Attribute {

    OrderType getType();

    enum OrderType {
        ASC, DESC
    }
}

