package br.edu.unigranrio.ect.si.cfa.commons.factory;

import br.edu.unigranrio.ect.si.cfa.commons.listener.AuditListener;

public final class AuditListenerFactory {

    private static final ThreadLocal<AuditListener> entityListener = new ThreadLocal<>();

    private AuditListenerFactory(){}

    public static synchronized AuditListener getEntityListener() {
        return entityListener.get();
    }

    public static synchronized void setEntityListener(AuditListener el) {
        entityListener.set(el);
    }

    public static synchronized void removeEntityListener() {
        entityListener.remove();
    }
}
