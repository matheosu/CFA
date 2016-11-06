package br.edu.unigranrio.ect.si.cfa.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;

@Entity
public class Controller extends BaseAuditable<Long> {

    private static final long serialVersionUID = 7723798680985178619L;

    public static final int MAX_LENGTH_UUID = 30;
    public static final int MAX_LENGTH_MODEL = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_controller")
    @SequenceGenerator(name = "generator_controller", sequenceName = "seq_controller", allocationSize = 1)
    private Long id;

    @Column(length = MAX_LENGTH_UUID, unique = true)
    private String uuid;

    @Column(length = MAX_LENGTH_MODEL)
    private String model;

    @ManyToOne
    @JoinColumn(name = "localization_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "localization_fk"))
    private Localization localization;

    @OneToMany(mappedBy = "controller")
    private List<Flow> flows;

    public Controller() {initCollections();}

    public Controller(String uuid, Localization localization) {
        setUuid(uuid);
        setLocalization(localization);
    }

    public Controller(String uuid, String model, Localization localization) {
        this(uuid, localization);
        setModel(model);
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
    public String toDescription() {
        return uuid + " " + model;
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

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public List<Flow> getFlows() {
        return flows;
    }

    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }

    private void initCollections(){
        setFlows(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Controller that = (Controller) o;

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
                "[ UUID: " + getUuid() + "] [ Model:" + getModel() + "]";
    }
}