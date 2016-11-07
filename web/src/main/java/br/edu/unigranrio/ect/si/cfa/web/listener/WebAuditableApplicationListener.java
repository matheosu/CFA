package br.edu.unigranrio.ect.si.cfa.web.listener;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;
import br.edu.unigranrio.ect.si.cfa.commons.model.listener.AuditableApplicationListener;
import br.edu.unigranrio.ect.si.cfa.commons.annotation.LoggedUserId;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;

import static br.edu.unigranrio.ect.si.cfa.commons.util.Constants.USER_ADMIN_ID;

@Named
public class WebAuditableApplicationListener implements AuditableApplicationListener, Serializable {

    private static final long serialVersionUID = -36248322712939881L;

    @Inject @LoggedUserId
    private Long loggedUserId;

    public WebAuditableApplicationListener() {
    }

    public WebAuditableApplicationListener(Long loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    @Override
    public <PK extends Number> void prePersist(Auditable<PK> entity) {
        entity.setDateCreated(Calendar.getInstance());
        entity.setUserCreatedId(loggedUserId != null ? loggedUserId : USER_ADMIN_ID);
        entity.setDateUpdated(Calendar.getInstance());
        entity.setUserUpdatedId(loggedUserId != null ? loggedUserId : USER_ADMIN_ID);
    }

    @Override
    public <PK extends Number> void preUpdate(Auditable<PK> entity) {
        entity.setDateUpdated(Calendar.getInstance());
        entity.setUserUpdatedId(loggedUserId != null ? loggedUserId : USER_ADMIN_ID);
    }
}
