package br.edu.unigranrio.ect.si.cfa.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@Entity
public class Localization extends BaseAuditable<Long> {

    private static final long serialVersionUID = -2065982378075234735L;

    public static final int MAX_LENGTH_NAME = 80;
    public static final int MAX_LENGTH_DESCRIPTION = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_localization")
    @SequenceGenerator(name = "generator_localization", sequenceName = "seq_localization", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "localization")
    private List<Controller> controllers;

    public Localization() {
        initCollections();
    }

    public Localization(String name) {
        setName(name);
    }

    public Localization(String name, String description) {
        this(name);
        setDescription(description);
    }

    public Localization(String name, String description, List<Controller> controllers) {
        this(name, description);
        setControllers(controllers);
    }

    private void initCollections(){
        setControllers(Collections.emptyList());
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

    public List<Controller> getControllers() {
        return controllers;
    }

    public void setControllers(List<Controller> controllers) {
        this.controllers = controllers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Localization that = (Localization) o;

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
                " [ Name: " + getName() + "] [ Description: " + getDescription() + "]";
    }
}