package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.Controller;

import java.util.List;
import java.util.Optional;

public interface ControllerService extends Service {

    Optional<Controller> findByUUID(String uuid);

    List<Controller> findByModel(String model);

}
