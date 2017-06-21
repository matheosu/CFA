package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.Controller;
import br.edu.unigranrio.ect.si.cfa.model.Controller_;
import br.edu.unigranrio.ect.si.cfa.service.ControllerService;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class ControllerServiceBean extends ServiceBean implements ControllerService {

    private static final long serialVersionUID = 32324067747514157L;

    @Override
    public Optional<Controller> findByUUID(String uuid) {
        return singleResult(comparing(Controller.class, Controller_.uuid, uuid));
    }

    @Override
    public List<Controller> findByModel(String model) {
        return resultList(comparing(Controller.class, Controller_.model, model));
    }

}
