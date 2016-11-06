package br.edu.unigranrio.ect.si.cfa.model;

import java.io.Serializable;

public interface Entity<PK extends Number> extends Serializable {

    PK getId();

    void setId(PK id);

    String toDescription();

    default boolean isIdNotNull(){
        return getId() != null && getId().longValue() != 0L;
    }

}
