package br.edu.unigranrio.ect.si.cfa.web.action;

import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class ConversationAction extends BaseAction {

    private static final long serialVersionUID = 5799159260582942358L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private Conversation conversation;

    public void initConversation() {
        if (facesContext.isPostback() && conversation.isTransient())
            conversation.begin();
    }

    public void endConversation() {
        if (!conversation.isTransient())
            conversation.end();
    }

    public Conversation getConversation() {
        return conversation;
    }
}
