package br.edu.unigranrio.ect.si.cfa.web.conversation;

import br.edu.unigranrio.ect.si.cfa.commons.model.Localization;
import br.edu.unigranrio.ect.si.cfa.service.LocalizationService;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.web.action.BaseAction;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class LocalizationAction extends BaseAction<Localization, Long> {

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
