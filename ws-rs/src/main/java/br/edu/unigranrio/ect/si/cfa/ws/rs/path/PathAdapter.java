package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.ValidObject;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.Collection;

public abstract class PathAdapter {

    protected <T> Response ok(T entity) {
        return response(Response.Status.OK, entity);
    }

    protected <E extends Entity, VO extends ValidObject<E>> Response ok(Class<VO> voClass, E entity) {
        return response(Response.Status.OK, voClass, entity);
    }

    protected Response noContent() {
        return Response.noContent().build();
    }

    protected Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    protected <E extends Entity, VO extends ValidObject<E>> Response response(Response.Status status, Class<VO> voClass, E entity) {
        if (entity == null)
            return notFound();

        return Response.status(status).entity(genericVO(voClass, entity)).build();
    }

    protected <T> Response response(Response.Status status, T entity) {
        if (entity == null)
            return notFound();
        if (Collection.class.isAssignableFrom(entity.getClass()))
            if (((Collection) entity).isEmpty())
                return notFound();

        return Response.status(status).entity(genericObject(entity)).build();
    }

    protected <E extends Entity, VO extends ValidObject<E>> GenericEntity<VO> genericVO(Class<VO> voClass, E entity) {
        VO vo = ValidObject.entity2Vo(voClass, entity);
        return vo != null ? new GenericEntity<VO>(vo, voClass) {
        } : null;
    }

    protected GenericEntity<Object> genericObject(Object object) {
        return new GenericEntity<Object>(object) {
        };
    }
}
