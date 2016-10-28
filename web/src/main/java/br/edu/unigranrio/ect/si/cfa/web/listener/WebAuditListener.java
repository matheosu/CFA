package br.edu.unigranrio.ect.si.cfa.web.listener;

import br.edu.unigranrio.ect.si.cfa.commons.listener.AuditListener;
import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;

import java.io.Serializable;
import java.util.Calendar;

import static br.edu.unigranrio.ect.si.cfa.commons.util.Constants.SYSTEM_ID;

public class WebAuditListener implements AuditListener, Serializable {

    private static final long serialVersionUID = -36248322712939881L;

    private final Long loggedUserId;

    public WebAuditListener(Long loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    @Override
    public <PK extends Serializable> void prePersist(Auditable<PK> entity) {
        entity.setDateCreated(Calendar.getInstance());
        entity.setUserCreatedId(loggedUserId != null ? loggedUserId : SYSTEM_ID);
        entity.setDateUpdated(Calendar.getInstance());
        entity.setUserUpdatedId(loggedUserId != null ? loggedUserId : SYSTEM_ID);
    }

    @Override
    public <PK extends Serializable> void preUpdate(Auditable<PK> entity) {
        entity.setDateUpdated(Calendar.getInstance());
        entity.setUserUpdatedId(loggedUserId != null ? loggedUserId : SYSTEM_ID);
    }
}
