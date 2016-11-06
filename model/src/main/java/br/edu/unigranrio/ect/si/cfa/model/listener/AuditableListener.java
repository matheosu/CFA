package br.edu.unigranrio.ect.si.cfa.model.listener;

import br.edu.unigranrio.ect.si.cfa.commons.listener.AuditListener;
import br.edu.unigranrio.ect.si.cfa.commons.factory.AuditListenerFactory;
import br.edu.unigranrio.ect.si.cfa.model.BaseAuditable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;

public class AuditableListener implements Serializable {

    private static final long serialVersionUID = -2129592208515731772L;

    @PrePersist
    public void prePersist(BaseAuditable auditable) {
        AuditListener listener = AuditListenerFactory.getEntityListener();
        if (listener != null)
            listener.prePersist(auditable);
    }

    @PreUpdate
    public void preUpdate(BaseAuditable auditable) {
        AuditListener listener = AuditListenerFactory.getEntityListener();
        if (listener != null)
            listener.preUpdate(auditable);
    }
}