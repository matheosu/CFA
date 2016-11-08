package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.model.Feature;
import br.edu.unigranrio.ect.si.cfa.model.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleVO extends ValidObject<Role> {

    private static final long serialVersionUID = 7368815660254227564L;

    private String name;
    private String description;
    private List<String> features;

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

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    @Override
    public void copy(Role entity) {
        super.copy(entity);
        this.setName(entity.getName());
        this.setDescription(entity.getDescription());
        this.setFeatures(entity.getFeatures().stream().map(Feature::getName).collect(Collectors.toList()));
    }


}
