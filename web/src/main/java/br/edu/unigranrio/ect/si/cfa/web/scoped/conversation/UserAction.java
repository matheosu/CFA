package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class UserAction extends ConversationAction<User, Long> {

    private static final long serialVersionUID = -3168685830620894551L;

    @Inject
    private UserService service;

    @Override
    protected Service service() {
        return service;
    }

    @Override
    protected User newInstance() {
        return new User();
    }

    @Override
    protected Long parseId(String id) {
        return Long.parseLong(id);
    }
}
