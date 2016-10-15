package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import java.io.Serializable;

public interface Service {

    <E extends Entity<PK>, PK extends Serializable> E find(Class<E> clazz, PK id);

    <E extends Entity<PK>, PK extends Serializable> E list(Class<E> clazz);

    <E extends Entity<PK>, PK extends Serializable> void save(E entity);

    <E extends Entity<PK>, PK extends Serializable> E update(E entity);

    <E extends Entity<PK>, PK extends Serializable> void remove(E entity);

}
