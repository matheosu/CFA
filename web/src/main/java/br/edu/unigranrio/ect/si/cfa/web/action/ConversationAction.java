package br.edu.unigranrio.ect.si.cfa.web.action;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class ConversationAction<E extends Entity<PK>, PK extends Number> extends BaseAction<E, PK> {

    private static final long serialVersionUID = 5799159260582942358L;

    private static final Logger logger = LoggerFactory.getLogger(ConversationAction.class);

    @Inject
    private FacesContext facesContext;

    @Inject
    private Conversation conversation;

    private void initConversation() {
        if (facesContext.isPostback() && conversation.isTransient()) {
            conversation.begin();
            logger.info("Init Conversation - Id: {}; Timeout: {}", conversation.getId(), conversation.getTimeout());
        }
    }

    private void endConversation() {
        if (!conversation.isTransient()) {
            logger.info("End Conversation - Id: {};", conversation.getId());
            conversation.end();
        }
    }

    @Override
    public String create() {
        initConversation();
        return super.create();
    }

    @Override
    public String edit() {
        initConversation();
        return super.edit();
    }

    @Override
    public String back() {
        endConversation();
        return super.back();
    }

    public Conversation getConversation() {
        return conversation;
    }
}
