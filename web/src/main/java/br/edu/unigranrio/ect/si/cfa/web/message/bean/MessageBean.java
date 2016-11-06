package br.edu.unigranrio.ect.si.cfa.web.message.bean;

import br.edu.unigranrio.ect.si.cfa.web.annotation.MessageResource;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named
public class MessageBean implements Message {

    private static final long serialVersionUID = -8085939994948874898L;

    @Inject @MessageResource
    private ResourceBundle bundle;

    @Override
    public Object get(String key) {
        return bundle.getObject(key);
    }
}
