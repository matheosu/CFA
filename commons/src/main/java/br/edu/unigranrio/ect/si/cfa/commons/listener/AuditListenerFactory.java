package br.edu.unigranrio.ect.si.cfa.commons.listener;

public final class AuditListenerFactory {

    private AuditListenerFactory(){}

    private static final ThreadLocal<AuditListener> entityListener = new ThreadLocal<>();

    public static AuditListener getEntityListener() {
        return entityListener.get();
    }

    public static void addEntityListener(AuditListener el) {
        entityListener.set(el);
    }

    public static void removeEntityListener() {
        entityListener.remove();
    }
}
