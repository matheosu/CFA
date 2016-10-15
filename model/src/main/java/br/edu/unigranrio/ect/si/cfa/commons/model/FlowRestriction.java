package br.edu.unigranrio.ect.si.cfa.commons.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@Entity
public class FlowRestriction extends BaseAuditable<Long> {

    private static final long serialVersionUID = 3230381671813980976L;

    public static final int MAX_LENGTH_NAME = 80;
    public static final int MAX_LENGTH_DESCRIPTION = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_flow_restriction")
    @SequenceGenerator(name = "generator_flow_restriction", sequenceName = "seq_flow_restriction", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Enumerated(EnumType.STRING)
    private Restriction restriction;

    @Column(nullable = false)
    private Float value;

    @OneToMany(mappedBy = "flowRestriction")
    private List<User> users;

    public FlowRestriction() {
        initCollections();
    }

    public FlowRestriction(String name, Period period, Restriction restriction, Float value) {
        setName(name);
        setPeriod(period);
        setRestriction(restriction);
        setValue(value);
    }

    public FlowRestriction(String name, String description, Period period, Restriction restriction, Float value) {
        this(name, period, restriction, value);
        setDescription(description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> user) {
        this.users = user;
    }

    private void initCollections(){
        setUsers(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FlowRestriction that = (FlowRestriction) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() +
                "[ Name: " + getName() + "] " +
                "[ Description: " + getDescription() + "] " +
                "[ Period: " + getPeriod() + "] " +
                "[ Restriction: " + getRestriction() + "] " +
                "[ Value: " + getValue() + "]";
    }
}