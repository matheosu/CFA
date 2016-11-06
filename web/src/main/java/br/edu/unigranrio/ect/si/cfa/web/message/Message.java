package br.edu.unigranrio.ect.si.cfa.web.message;

import java.io.Serializable;

@FunctionalInterface
public interface Message extends Serializable {

    Object get(String key);

    default String getString(String key){
        Object o = key != null ? get(key.toLowerCase()) : null;
        return o != null ? String.valueOf(o) : "";
    }

}
