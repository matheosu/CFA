package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.model.Controller;
import br.edu.unigranrio.ect.si.cfa.model.Localization;
import br.edu.unigranrio.ect.si.cfa.service.ControllerService;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ConversationScoped
public class ControllerAction extends ConversationAction<Controller,Long> {

    private static final long serialVersionUID = 2188919582365313621L;

    @Inject
    private ControllerService service;

    @Override
    protected Service service() {
        return service;
    }

    @Override
    protected Controller newInstance() {
        return new Controller();
    }

    @Override
    protected Long parseId(String id) {
        return Long.valueOf(id);
    }

    public List<Localization> getLocalizations() {
        return service().list(Localization.class);
    }
}
