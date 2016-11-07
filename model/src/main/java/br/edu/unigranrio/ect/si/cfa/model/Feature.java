package br.edu.unigranrio.ect.si.cfa.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@Entity
public class Feature extends BaseEntity<Long> {

    private static final long serialVersionUID = 9028582384847994851L;

    public static final int MAX_LENGTH_NAME = 50;
    public static final int MAX_LENGTH_DESCRIPTION = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_feature")
    @SequenceGenerator(name = "generator_feature", sequenceName = "seq_feature", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_LENGTH_NAME)
    private String name;

    @Column(length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(nullable = false, unique = true)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependency_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "dependency_fk"))
    private Feature dependency;

    @OneToMany(mappedBy = "dependency")
    private List<Feature> dependents;

    @ManyToMany(mappedBy = "features")
    private List<Role> roles;

    public Feature() {
        initCollections();
    }

    public Feature(Long id, String name, String url) {
        setId(id);
        setName(name);
        setUrl(url);
    }

    public Feature(Long id, String name, String description, String url) {
        this(id, name, url);
        setDescription(description);
    }

    public Feature(Long id, String name, String description, String url, Feature dependency) {
        this(id, name, description, url);
        setDependency(dependency);
    }

    public Feature(Long id, String name, String description, String url, List<Role> roles) {
        this(id, name, description, url);
        setRoles(roles);
    }

    public Feature(Long id, String name, String description, String url, Feature dependency, List<Role> roles) {
        this(id, name, description, url, dependency);
        setRoles(roles);
    }

    private void initCollections(){
        setRoles(Collections.emptyList());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean hasReference() {
        return (roles != null && !roles.isEmpty()) || (dependents != null && !dependents.isEmpty());
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Feature getDependency() {
        return dependency;
    }

    public void setDependency(Feature dependency) {
        this.dependency = dependency;
    }

    public List<Feature> getDependents() {
        return dependents;
    }

    public void setDependents(List<Feature> dependents) {
        this.dependents = dependents;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Feature feature = (Feature) o;

        return getId().equals(feature.getId());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return  super.toString() +
                " [ Name:" + getName()  + "] [ Description: " + getDescription() + "]";
    }
}