package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.service.LocalMessage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Named("localMessage")
@ApplicationScoped
public class LocalMessageBean implements LocalMessage {

    private static final long serialVersionUID = 8503069204285211529L;

    private ResourceBundle bundle;

    @PostConstruct
    public void init() {
        bundle = ResourceBundle.getBundle("/localMessage", Locale.getDefault());
    }

    public String get(String key, Object... args) {
        return MessageFormat.format(bundle.getString(key), args);
    }

}
