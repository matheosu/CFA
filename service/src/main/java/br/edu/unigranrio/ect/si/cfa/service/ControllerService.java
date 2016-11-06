package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.Controller;

import java.util.List;

public interface ControllerService extends Service {

    Controller findByUUID(String uuid);

    List<Controller> findByModel(String model);

}
