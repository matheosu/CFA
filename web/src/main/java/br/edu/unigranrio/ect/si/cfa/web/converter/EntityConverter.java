package br.edu.unigranrio.ect.si.cfa.web.converter;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("entity-converter")
public class EntityConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && !s.isEmpty())
            return uiComponent.getAttributes().get(s);

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null && o instanceof Entity) {
            Entity entity = (Entity) o;
            String key = entity.getId() == null ? String.valueOf(entity.hashCode()) : entity.getClass().getSimpleName() + entity.getId();
            uiComponent.getAttributes().put(key, entity);
            return key;
        }
        return null;
    }
}
