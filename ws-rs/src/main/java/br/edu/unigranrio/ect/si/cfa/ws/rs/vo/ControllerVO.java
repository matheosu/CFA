package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.model.Controller;

public class ControllerVO extends ValidObject<Controller> {

    private static final long serialVersionUID = 1404032770638843767L;

    private String uuid;
    private String model;
    private LocalizationVO localization;

    private AuditedInfo audited;

    public ControllerVO() {
    }

    public ControllerVO(Controller controller) {
        copy(controller);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalizationVO getLocalization() {
        return localization;
    }

    public AuditedInfo getAudited() {
        return audited;
    }

    public void setAudited(AuditedInfo audited) {
        this.audited = audited;
    }

    public void setLocalization(LocalizationVO localization) {
        this.localization = localization;
    }

    @Override
    public void copy(Controller entity) {
        super.copy(entity);
        if (entity != null) {
            this.setUuid(entity.getUuid());
            this.setModel(entity.getModel());
            this.setAudited(new AuditedInfo(entity));
            this.setLocalization(new LocalizationVO(entity.getLocalization()));
        }
    }
}
