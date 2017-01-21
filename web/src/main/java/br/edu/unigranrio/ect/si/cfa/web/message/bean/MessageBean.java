package br.edu.unigranrio.ect.si.cfa.web.message.bean;

import br.edu.unigranrio.ect.si.cfa.web.annotation.MessageResource;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named
public class MessageBean implements Message {

    private static final long serialVersionUID = -8085939994948874898L;

    private final ResourceBundle bundle;

    @Inject
    public MessageBean(@MessageResource ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public Object get(String key) {
        try {
            return bundle.getObject(key);
        } catch (Exception e) {
            return null;
        }
    }
}
