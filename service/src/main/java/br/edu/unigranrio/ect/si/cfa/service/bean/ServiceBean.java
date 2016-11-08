package br.edu.unigranrio.ect.si.cfa.service.bean;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import br.edu.unigranrio.ect.si.cfa.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ServiceBean implements Service {

    private static final long serialVersionUID = -4097016207010149674L;

    private static final Logger logger = LoggerFactory.getLogger(ServiceBean.class);

    @Inject
    protected EntityManager em;

    @Override
    public <E extends Entity<PK>, PK extends Number> E find(Class<E> clazz, PK id) {
        try { return em.find(clazz, id); } catch(NoResultException e){ return null; }
    }

    @Override
    public <E extends Entity<?>> List<E> list(Class<E> clazz) {
        CriteriaUtil<E, E> util = createCriteriaUtil(clazz);
        CriteriaQuery<E> query = util.getQuery();
        return resultList(query);
    }

    @Override
    public <E extends Entity<?>> void save(E entity) {
        em.persist(entity);
    }

    @Override
    public <E extends Entity<?>> E update(E entity) {
        return em.merge(entity);
    }

    @Override
    public <E extends Entity<?>> void remove(E entity) {
        em.remove(entity);
    }

    @Override
    public <E extends Entity<?>> void refresh(E entity) {
        em.refresh(entity);
    }

    @Override
    public <E extends Entity<?>> boolean contains(E entity) {
        return em.contains(entity);
    }

    @Override
    public  <E extends Entity<?>> Long count(Class<E> from) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        return singleResult(query.select(cb.count(query.from(from))));
    }

    protected <E> E singleResult(TypedQuery<E> query) {
        try {
            return Objects.requireNonNull(query).getSingleResult();
        } catch (NoResultException e) {
            logger.debug("NoResultException {}", e);
        } catch (NonUniqueResultException nure) {
            logger.warn("NonUniqueResult {}", nure);
        } catch (Exception e) {
            logger.error("Error in singleResult on {}", query, e);
        }
        return null;
    }

    protected <E> E singleResult(CriteriaQuery<E> query) {
        return singleResult(em.createQuery(query));
    }

    protected <E> List<E> resultList(TypedQuery<E> query) {
        try {
            return Objects.requireNonNull(query).getResultList();
        } catch (Exception e) {
            logger.error("Error in resultList on {}", query, e);
        }
        return Collections.emptyList();
    }

    protected <E> List<E> resultList(CriteriaQuery<E> query) {
        return resultList(em.createQuery(query));
    }


    protected <E extends Entity<?>> CriteriaUtil<E, E> createCriteriaUtil(Class<E> clazz) {
        return createCriteriaUtil(clazz, clazz);
    }

    protected <E extends Serializable, R extends Entity<?>> CriteriaUtil<E, R> createCriteriaUtil(Class<E> result, Class<R> from) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(result);
        return new CriteriaUtil<>(query, query.from(from));
    }


    protected <E extends Serializable, R extends Entity<?>> CriteriaQuery<R>
            comparing(Class<R> from, SingularAttribute<R, E> attr, E value) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUtil<R, R> util = createCriteriaUtil(from);
        CriteriaQuery<R> query = util.getQuery();

        return query.where(cb.equal(util.getFrom().get(attr), value));
    }

    class CriteriaUtil<S, R extends Entity<?>> {

        private CriteriaQuery<S> query;
        private Root<R> from;

        public CriteriaUtil(CriteriaQuery<S> query, Root<R> from) {
            this.query = query;
            this.from = from;
        }

        public CriteriaQuery<S> getQuery() {
            return query;
        }

        public Root<R> getFrom() {
            return from;
        }
    }
}
