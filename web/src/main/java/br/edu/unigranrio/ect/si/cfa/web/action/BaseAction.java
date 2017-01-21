package br.edu.unigranrio.ect.si.cfa.web.action;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.web.annotation.RequestParam;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;
import br.edu.unigranrio.ect.si.cfa.web.message.WebMessage;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseAction<E extends Entity<PK>, PK extends Number> implements Action {

    private static final long serialVersionUID = -2353426370709926798L;
    private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

    @Inject private Message message;
    @Inject private WebMessage webMessage;
    @Inject @RequestParam private String requestId;

    private E instance;
    private Class<E> entityClass;
    private List<E> instances = Collections.emptyList();

    @Produces
    protected abstract E newInstance();
    protected abstract Service service();
    protected abstract PK parseId(String id);

    @Override
    public String create() {
        logger.info("Creating new instance from {}", entityClass());
        setInstance(newInstance());
        return Pages.actionEdit(entityClass(), true);
    }

    @Override
    @Transactional
    public String save() {
        if (!getInstance().isIdNotNull()) {
            getInstance().setId(null);
            logger.info("Saving instance from {}", entityClass());
            service().save(getInstance());
        } else {
            logger.info("Update instance from {}; Id: {}", entityClass(), getInstance().getId());
            service().update(getInstance());
        }

        instances = Collections.emptyList();
        return back();
    }

    @Override
    public String edit() {
        logger.info("Loading instance from {}; Id: {}", entityClass(), requestId());
        Optional<E> instance = service().find(entityClass(), requestId());
        setInstance(instance.orElse(null));
        return Pages.actionEdit(entityClass());
    }

    @Override
    @Transactional
    public String delete() {
        Optional<E> e = service().find(entityClass(),requestId());
        if(e.isPresent() && !e.get().hasReference()) {
            logger.info("Delete instance from {}; Id: {}", entityClass(), requestId());
            service().remove(e.get());
        } else {
            logger.warn("Cannot delete, instance has references; {}", entityClass());
            webMessage.warn(message.getString("delete.has.references"));
        }
        instances = Collections.emptyList();
        return back();
    }

    @Override
    public String list() {
        instances = Collections.emptyList();
        return Pages.actionList(entityClass(), true);
    }

    @Override
    public String back() {
        setInstance(newInstance());
        return list();
    }

    @Override
    public boolean isManaged() {
        return instance != null && instance.isIdNotNull();
    }

    public E getInstance() {
        return instance;
    }

    public void setInstance(E instance) {
        this.instance = instance;
    }

    public List<E> getInstances() {
        if(instances == null || instances.isEmpty()) {
            logger.info("Loading all instance from {};", entityClass());
            instances = service().list(entityClass());
        }

        return instances;
    }

    private PK requestId() {
        return requestId != null ? parseId(requestId) : null;
    }

    @SuppressWarnings("unchecked")
    private Class<E> entityClass() {
        if (entityClass == null) {
            Type type = ((ParameterizedType) getClass().getSuperclass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            entityClass = (Class<E>) type;
        }
        return entityClass;
    }

}