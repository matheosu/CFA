package br.edu.unigranrio.ect.si.cfa.commons.model;

import java.io.Serializable;

public interface Entity<PK extends Number> extends Serializable {

    PK getId();

    void setId(PK id);

    default boolean isIdNotNull(){
        return getId() != null && getId().longValue() != 0L;
    }

}
