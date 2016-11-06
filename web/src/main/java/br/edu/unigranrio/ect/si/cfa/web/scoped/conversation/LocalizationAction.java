package br.edu.unigranrio.ect.si.cfa.web.scoped.conversation;

import br.edu.unigranrio.ect.si.cfa.model.Localization;
import br.edu.unigranrio.ect.si.cfa.service.LocalizationService;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.web.action.ConversationAction;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class LocalizationAction extends ConversationAction<Localization, Long> {

    private static final long serialVersionUID = -8514697772543915412L;

    @Inject LocalizationService service;

    @Override
    protected Service service() {
        return service;
    }

    @Override
    protected Localization newInstance() {
        return new Localization();
    }

    @Override
    protected Long parseId(String id) {
        return Long.valueOf(id);
    }

}
