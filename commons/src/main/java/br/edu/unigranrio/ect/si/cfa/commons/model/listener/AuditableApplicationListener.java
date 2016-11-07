package br.edu.unigranrio.ect.si.cfa.commons.model.listener;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;

public interface AuditableApplicationListener {

    <PK extends Number> void prePersist(Auditable<PK> entity);

    <PK extends Number> void preUpdate(Auditable<PK> entity);

}
