package br.edu.unigranrio.ect.si.cfa.web.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesContextProducer {

    @Produces @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }


}
