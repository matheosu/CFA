package br.edu.unigranrio.ect.si.cfa.commons.listener;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;

import java.io.Serializable;

public interface AuditListener {

    <PK extends Serializable> void prePersist(Auditable<PK> entity);

    <PK extends Serializable> void preUpdate(Auditable<PK> entity);

}
