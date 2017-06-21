package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.model.Localization;
import br.edu.unigranrio.ect.si.cfa.model.Localization_;
import br.edu.unigranrio.ect.si.cfa.service.LocalizationService;

import javax.inject.Named;
import java.util.Optional;

@Named
public class LocalizationServiceBean extends ServiceBean implements LocalizationService {

    private static final long serialVersionUID = 9005938490309568180L;

    @Override
    public Optional<Localization> findLocalizationByName(String name) {
        return singleResult(comparing(Localization.class, Localization_.name, name));
    }
}
