package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "\"User\"")
public class User extends BaseAuditable<Long> {

    private static final long serialVersionUID = -5666710091863571387L;

    /**
     * Default System User ID
     */
    public static final Long systemUserId = 1L;

    public static final int MAX_LENGTH_NAME = 200;
    public static final int MAX_LENGTH_EMAIL = 400;
    public static final int MAX_LENGTH_PASSWORD = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_user")
	@SequenceGenerator(name = "generator_user", sequenceName = "seq_user", allocationSize = 1, initialValue = 100)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(unique = true, nullable = false, length = MAX_LENGTH_EMAIL)
    private String email;

    @Column(nullable = false, length = MAX_LENGTH_PASSWORD)
    private String password;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "role_fk"))
    private Role role;

    @ManyToOne
    @JoinColumn(name = "flowRestriction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "flowRestriction_fk"))
    private FlowRestriction flowRestriction;

    @OneToMany(mappedBy = "user")
    private List<SessionUser> sessions;

    @OneToMany(mappedBy = "user")
    private List<Flow> flows;


    public User() { initCollections(); }

    private User(String name, String password, Role role) {
        this();
        setName(name);
        setPassword(password);
        setActive(Boolean.TRUE);
        setRole(role);
    }

    public User(String name, String email, String password, Role role) {
        this(name, password, role);
        setEmail(email);
    }

    public User(String name, String email, String password, Role role, Boolean active) {
        this(name, email, password, role);
        setActive(active);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<SessionUser> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionUser> sessionUsers) {
        this.sessions = sessionUsers;
    }

    public List<Flow> getFlows() {
        return flows;
    }

    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }

    public FlowRestriction getFlowRestriction() {
        return flowRestriction;
    }

    public void setFlowRestriction(FlowRestriction flowRestriction) {
        this.flowRestriction = flowRestriction;
    }

    private void initCollections() {
        setSessions(Collections.emptyList());
        setFlows(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        return getId().equals(user.getId());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return super.toString()
                + " [ Name: " + getName() + "]"
                + " [ Email: " + getEmail() + "]"
                + " [ Active: " + isActive() + "]"
                + " [ Role: " + getRole() + "]"
                + " [ FlowRestriction: " + getFlowRestriction() + "]";
    }
}