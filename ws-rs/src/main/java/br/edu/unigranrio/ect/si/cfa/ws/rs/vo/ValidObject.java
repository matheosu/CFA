package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

import java.io.Serializable;

public abstract class ValidObject<E extends Entity> implements Serializable, TranscribeValues<E> {

    private static final long serialVersionUID = -2081178230667786152L;

    private Number id;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    @Override
    public void copy(E entity) {
        this.setId(entity.getId());
    }


    public static <E extends Entity, VO extends ValidObject<E>> VO entity2Vo(Class<VO> voClass, E entity) {
        try {
            VO vo = voClass.newInstance();
            vo.copy(entity);
            return vo;
        } catch (InstantiationException | IllegalAccessException ignored) {
        }
        return null;
    }
}
