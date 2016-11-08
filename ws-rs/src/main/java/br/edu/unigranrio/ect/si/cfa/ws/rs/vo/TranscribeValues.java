package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;

/**
 * Interface that transcribe values from entity model
 * @param <E>
 */
public interface TranscribeValues<E extends Entity> {

    void copy(E entity);

}
