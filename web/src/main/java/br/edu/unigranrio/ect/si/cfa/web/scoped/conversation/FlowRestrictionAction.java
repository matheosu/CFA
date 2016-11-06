package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.model.FlowRestriction;
import br.edu.unigranrio.ect.si.cfa.model.Restriction;
import br.edu.unigranrio.ect.si.cfa.service.FlowRestrictionService;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ConversationScoped
public class FlowRestrictionAction extends ConversationAction<FlowRestriction, Long> {

    private static final long serialVersionUID = -2722210104925527140L;

    @Inject
    private FlowRestrictionService service;

    @Inject
    private Message msg;

    @Override
    protected FlowRestriction newInstance() {
        return new FlowRestriction();
    }

    @Override
    protected Service service() {
        return service;
    }

    @Override
    protected Long parseId(String id) {
        return Long.valueOf(id);
    }

    public List<SelectItem> getRestrictions() {
        return Arrays.stream(Restriction.RestrictionType.values())
                .map(t -> new SelectItemGroup(msg.getString(t.name()), t.name(), false, getRestrictionsByType(t)))
                .collect(Collectors.toList());
    }

    private SelectItem[] getRestrictionsByType(final Restriction.RestrictionType type) {
        return Arrays.stream(Restriction.values())
                .filter(r -> r.getType().equals(type))
                .map(r -> new SelectItem(r, msg.getString(r.name())))
                .toArray(SelectItem[]::new);
    }
}
