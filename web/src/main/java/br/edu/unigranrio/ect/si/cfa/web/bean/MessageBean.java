package br.edu.unigranrio.ect.si.cfa.web.bean;

import br.edu.unigranrio.ect.si.cfa.service.exception.AuthException;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("message")
public class MessageBean implements Message {

    private static final long serialVersionUID = -6451232072778461226L;

    @Inject FacesContext context;

    @Override
    public void msg(FacesMessage.Severity status, String msg, String details, String idPage) {
        context.addMessage(idPage, new FacesMessage(status, msg, details));
    }

}
