package br.edu.unigranrio.ect.si.cfa.model;

import br.edu.unigranrio.ect.si.cfa.commons.util.DateTimeUtils;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Calendar;

@Entity
public class Flow extends BaseEntity<Long> {

    private static final long serialVersionUID = -3141442875732879250L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_flow")
    @SequenceGenerator(name = "generator_flow", sequenceName = "seq_flow", allocationSize = 1)
    private Long id;

    /**
     * Measure in litter
     */
    @Column(nullable = false)
    private Float measure;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registrantion;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_fk"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "controller_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "controller_fk"))
    private Controller controller;

    public Flow() {
    }

    public Flow(Float measure, Calendar registrantion, User user, Controller controller) {
        setMeasure(measure);
        setRegistrantion(registrantion);
        setUser(user);
        setController(controller);
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
        return user != null || controller != null;
    }

    public Float getMeasure() {
        return measure;
    }

    public void setMeasure(Float measure) {
        this.measure = measure;
    }

    public Calendar getRegistrantion() {
        return registrantion;
    }

    public void setRegistrantion(Calendar initialDate) {
        this.registrantion = initialDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Flow flow = (Flow) o;

        return getId().equals(flow.getId());

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
                "[ Measure: " + getMeasure() + "]" +
                "[ Registration: " + DateTimeUtils.calendar2String(getRegistrantion()) + "] ";
    }
}