package br.edu.unigranrio.ect.si.cfa.service;

import java.io.Serializable;

@FunctionalInterface
public interface LocalMessage extends Serializable {

    String get(String key, Object... args);

    default String get(String key) {
        return get(key, (Object[]) null);
    }
}
