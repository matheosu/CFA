package br.edu.unigranrio.ect.si.cfa.web.action;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.web.annotation.RequestParam;
import br.edu.unigranrio.ect.si.cfa.web.util.Pages;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

abstract class BaseAction<E extends Entity<PK>, PK extends Serializable> implements Action {

    private static final long serialVersionUID = -2353426370709926798L;

    @Inject
    @RequestParam
    private String id;

    private E instance;
    private Class<E> clazz;

    protected abstract E newInstance();
    protected abstract PK parseId(String id);
    protected abstract Service service();

    @Override
    public String create() {
        setInstance(newInstance());
        return Pages.actionEdit(clazz(), true);
    }

    @Override
    @Transactional
    public String save() {
        if (id == null || id.isEmpty()) {
            getInstance().setId(null);
            service().save(instance);
        } else {
            setInstance(service().update(instance));
        }

        return back();
    }

    @Override
    public String edit() {
        setInstance(service().find(clazz(), parseId(id)));
        return Pages.actionEdit(clazz());
    }

    @Override
    @Transactional
    public String delete() {
        setInstance(service().find(clazz(), parseId(id)));
        service().remove(getInstance());
        return back();
    }

    @Override
    public String back() {
        setInstance(newInstance());
        return Pages.actionList(clazz());
    }

    public E getInstance() {
        return instance;
    }

    public void setInstance(E instance) {
        this.instance = instance;
    }


    @SuppressWarnings("unchecked")
    private Class<E> clazz() {
        if (clazz == null) {
            Type type = ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            clazz = (Class<E>) type;
        }
        return clazz;
    }

}
