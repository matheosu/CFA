package br.edu.unigranrio.ect.si.cfa.commons.model;

import java.io.Serializable;

public interface Entity<PK extends Serializable> extends Serializable {

    PK getId();

    void setId(PK id);

}
