package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import java.io.Serializable;

public interface Service extends Serializable {

    <E extends Entity<PK>, PK extends Serializable> E find(Class<E> clazz, PK id);

    <E extends Entity<?>> E list(Class<E> clazz);

    <E extends Entity<?>> void save(E entity);

    <E extends Entity<?>> E update(E entity);

    <E extends Entity<?>> void remove(E entity);

    <E extends Entity<?>> void refresh(E entity);

    <E extends Entity<?>> boolean contains(E entity);
}
