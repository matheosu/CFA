package br.edu.unigranrio.ect.si.cfa.commons.listener;

import br.edu.unigranrio.ect.si.cfa.model.Auditable;

public interface AuditListener {

    <PK extends Number> void prePersist(Auditable<PK> entity);

    <PK extends Number> void preUpdate(Auditable<PK> entity);

}
