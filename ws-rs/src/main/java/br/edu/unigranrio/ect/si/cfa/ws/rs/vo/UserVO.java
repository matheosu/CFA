package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.model.User;

public class UserVO extends ValidObject<User> {

    private static final long serialVersionUID = 2295328806280502044L;

    private String name;
    private String email;
    private Boolean active;
    private RoleVO role;
    private AuditedInfo audited;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleVO getRole() {
        return role;
    }

    public void setRole(RoleVO role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public AuditedInfo getAudited() {
        return audited;
    }

    public void setAudited(AuditedInfo audited) {
        this.audited = audited;
    }

    @Override
    public void copy(User entity) {
        super.copy(entity);
        this.setName(entity.getName());
        this.setEmail(entity.getEmail());
        this.setActive(entity.getActive());
        this.setRole(entity2Vo(RoleVO.class, entity.getRole()));
        this.setAudited(new AuditedInfo(entity));
    }
}
