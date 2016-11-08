package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.model.Localization;

public class LocalizationVO extends ValidObject<Localization>{

    private static final long serialVersionUID = -4042720722769344819L;

    private String name;
    private String description;
    private AuditedInfo audited;

    public LocalizationVO() {
    }


    public LocalizationVO(Localization entity) {
        copy(entity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuditedInfo getAudited() {
        return audited;
    }

    public void setAudited(AuditedInfo audited) {
        this.audited = audited;
    }

    @Override
    public void copy(Localization entity) {
        super.copy(entity);
        if (entity != null) {
            this.setName(entity.getName());
            this.setDescription(entity.getDescription());
            this.setAudited(new AuditedInfo(entity));
        }
    }
}
