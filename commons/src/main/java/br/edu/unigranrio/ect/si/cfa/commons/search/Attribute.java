package br.edu.unigranrio.ect.si.cfa.commons.search;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import javax.persistence.metamodel.SingularAttribute;

public interface Attribute {

    <X extends Entity<?>, T> SingularAttribute<X, T> getAttribute();
}
