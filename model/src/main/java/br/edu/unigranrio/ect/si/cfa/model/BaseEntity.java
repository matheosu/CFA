package br.edu.unigranrio.ect.si.cfa.model;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<PK extends Number> implements Entity<PK> {

    private static final long serialVersionUID = -6355720954167694592L;

    @Override
    public abstract PK getId();

    @Override
    public abstract void setId(PK id);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> other = (BaseEntity<?>) o;
        return getId() != null ? getId().equals(other.getId()) : other.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ ID: " + getId() + " ]";
    }
}
