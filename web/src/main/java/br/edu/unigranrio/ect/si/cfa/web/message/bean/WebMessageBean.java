package br.edu.unigranrio.ect.si.cfa.web.message.bean;

import br.edu.unigranrio.ect.si.cfa.web.message.WebMessage;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("message")
public class WebMessageBean implements WebMessage {

    private static final long serialVersionUID = -6451232072778461226L;

    @Inject FacesContext context;

    @Override
    public void msg(FacesMessage.Severity status, String msg, String details, String idPage) {
        context.addMessage(idPage, new FacesMessage(status, msg, details));
    }

}
