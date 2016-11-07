package br.edu.unigranrio.ect.si.cfa.commons.model.listener;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;

public class AuditableListener implements Serializable {

    private static final long serialVersionUID = -2129592208515731772L;

    @PrePersist
    public void prePersist(Object auditable) {
        AuditableApplicationListener applicationListener = getAuditableApplicationListener();
        if (auditable instanceof Auditable)
            if (applicationListener != null)
                applicationListener.prePersist((Auditable) auditable);
    }

    private AuditableApplicationListener getAuditableApplicationListener() {
        BeanManager beanManager = CDI.current().getBeanManager();
        Bean bean = beanManager.resolve(beanManager.getBeans(AuditableApplicationListener.class));
        CreationalContext creationalContext = beanManager.createCreationalContext(bean);
        return (AuditableApplicationListener) beanManager.getReference(bean, AuditableApplicationListener.class, creationalContext);
    }

    @PreUpdate
    public void preUpdate(Object auditable) {
        AuditableApplicationListener applicationListener = getAuditableApplicationListener();
        if (auditable instanceof Auditable)
            if (applicationListener != null)
                applicationListener.preUpdate((Auditable) auditable);
    }
}