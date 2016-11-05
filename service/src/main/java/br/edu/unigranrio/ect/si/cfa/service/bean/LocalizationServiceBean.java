package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.Localization;
import br.edu.unigranrio.ect.si.cfa.commons.model.Localization_;
import br.edu.unigranrio.ect.si.cfa.service.LocalizationService;

import javax.inject.Named;

@Named
public class LocalizationServiceBean extends ServiceBean implements LocalizationService {

    private static final long serialVersionUID = 9005938490309568180L;

    @Override
    public Localization findLocalizationByName(String name) {
        return singleResult(comparing(Localization.class, Localization_.name, name));
    }
}
