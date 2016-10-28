package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.commons.model.Localization;

public interface LocalizationService extends Service {

    Localization findLocalizationByName(String name);

}