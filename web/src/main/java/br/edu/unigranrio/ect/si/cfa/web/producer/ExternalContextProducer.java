package br.edu.unigranrio.ect.si.cfa.web.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ExternalContextProducer {

    @Produces @RequestScoped
    public ExternalContext getExternalContext(FacesContext facesContext) {
        return facesContext.getExternalContext();
    }
}
