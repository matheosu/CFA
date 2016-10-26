package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.Feature;
import br.edu.unigranrio.ect.si.cfa.commons.model.Role;

import java.util.List;

public interface RoleService extends Service {

    List<Role> findRolesByName(String name);

    List<Role> findRolesByFeatures(List<Feature> features);

}
