package br.edu.unigranrio.ect.si.cfa.web.action;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.web.annotation.RequestParam;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BaseAction<E extends Entity<PK>, PK extends Serializable> implements Action {

    private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

    private static final long serialVersionUID = -2353426370709926798L;

    @Inject @RequestParam
    protected String requestId;

    private Class<E> entityClass;

    private E instance;
    private List<E> instances = Collections.emptyList();

    protected abstract Service service();
    protected abstract E newInstance();
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
        if (getInstance().getId() == null) {
            getInstance().setId(null);
            logger.info("Saving instance from {}", entityClass());
            service().save(getInstance());
        } else {
            logger.info("Update instance from {}; Id: {}", entityClass(), getInstance().getId());
//            E managed = service().find(entityClass(), getInstance().getId());
//            copyValues(managed, getInstance());
            service().update(getInstance());
        }

        instances = Collections.emptyList();
        return back();
    }

    private void copyValues(E managedEntity, E jsfEntity) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass());
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                Object value = descriptor.getReadMethod().invoke(jsfEntity);

                if (value != null) {
                    if (Collection.class.isAssignableFrom(descriptor.getPropertyType())) {
                        if (!((Collection) value).isEmpty())
                            descriptor.getWriteMethod().invoke(managedEntity, value);
                    } else {
                        descriptor.getWriteMethod().invoke(managedEntity, value);
                    }
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            logger.error("Error to copy values from jsfEntity to managedEntity; Exception {}", e);
        }
    }

    @Override
    public String edit() {
        logger.info("Loading instance from {}; Id: {}", entityClass(), requestId);
        setInstance(service().find(entityClass(), parseId(requestId)));
        return Pages.actionEdit(entityClass());
    }

    @Override
    @Transactional
    public String delete() {
        logger.info("Delete instance from {}; Id: {}", entityClass(), requestId);
        service().remove(service().find(entityClass(), parseId(requestId)));
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
        return Pages.actionList(entityClass(), true);
    }

    public E getInstance() {
        if(instance == null)
            instance = newInstance();
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

    @SuppressWarnings("unchecked")
    private Class<E> entityClass() {
        if (entityClass == null) {
            Type type = ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            entityClass = (Class<E>) type;
        }
        return entityClass;
    }

}