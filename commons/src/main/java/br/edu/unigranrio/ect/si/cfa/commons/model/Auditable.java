package br.edu.unigranrio.ect.si.cfa.commons.model;

import java.io.Serializable;
import java.util.Calendar;

public interface Auditable<PK extends Number> extends Entity<PK>{

    Long getUserCreatedId();

    void setUserCreatedId(Long userCreatedId);

    Calendar getDateCreated();

    void setDateCreated(Calendar dateCreated);

    Long getUserUpdatedId();

    void setUserUpdatedId(Long userUpdatedId);

    Calendar getDateUpdated();

    void setDateUpdated(Calendar dateUpdated);

    Integer getVersion();

    void setVersion(Integer version);

}
