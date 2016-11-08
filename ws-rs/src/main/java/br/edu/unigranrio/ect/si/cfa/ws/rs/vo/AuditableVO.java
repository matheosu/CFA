package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;

public abstract class AuditableVO<E extends Auditable> extends ValidObject<E> {

    private static final long serialVersionUID = 9103535624174969607L;

    private AuditedInfo audited;

    public AuditedInfo getAudited() {
        return audited;
    }

    public void setAudited(AuditedInfo audited) {
        this.audited = audited;
    }

    @Override
    public void copy(E entity) {
        super.copy(entity);
        if (this.audited == null)
            this.audited = new AuditedInfo(entity);
    }

}
