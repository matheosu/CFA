package br.edu.unigranrio.ect.si.cfa.web.producer;

import br.edu.unigranrio.ect.si.cfa.web.annotation.MessageResource;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public class MessageResourceProducer {

    @Produces @MessageResource
    public ResourceBundle gerMessageResource(FacesContext context) {
        return context.getApplication().getResourceBundle(context, "msg");
    }
}
