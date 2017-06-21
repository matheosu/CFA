package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.Localization;

import java.util.Optional;

public interface LocalizationService extends Service {

    Optional<Localization> findLocalizationByName(String name);

}