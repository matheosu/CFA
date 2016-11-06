package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.model.Feature;
import br.edu.unigranrio.ect.si.cfa.model.Role;
import br.edu.unigranrio.ect.si.cfa.service.RoleService;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ConversationScoped
public class RoleAction extends ConversationAction<Role, Long> {

    private static final long serialVersionUID = 5888344693047818484L;

    @Inject
    private RoleService service;

    @Override
    protected Service service() {
        return service;
    }

    @Override
    protected Role newInstance() {
        return new Role();
    }

    @Override
    protected Long parseId(String id) {
        return Long.parseLong(id);
    }

    public List<Feature> getFeatures() {
        return service().list(Feature.class);
    }
}
