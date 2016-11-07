package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.model.FlowRestriction;
import br.edu.unigranrio.ect.si.cfa.model.Role;
import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;
import br.edu.unigranrio.ect.si.cfa.commons.annotation.LoggedUserId;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ConversationScoped
public class UserAction extends ConversationAction<User, Long> {

    private static final long serialVersionUID = -3168685830620894551L;

    @Inject
    private UserService service;

    @Inject @LoggedUserId
    private Long loggedUserId;

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

    public List<Role> getRoles() {
        return service.list(Role.class);
    }

    public List<FlowRestriction> getFlowRestrictions() {
        return service.list(FlowRestriction.class);
    }

    /**
     * Prevent the logged user can edit yourself for this action; <br/>
     * Including Administrator
     * @return A new user list without logged user
     */
    @Override
    public List<User> getInstances() {
        return super.getInstances().stream()
                .filter(u -> !u.getId().equals(loggedUserId))
                .filter(u -> !u.getId().equals(Constants.USER_ADMIN_ID))
                .collect(Collectors.toList());
    }
}
