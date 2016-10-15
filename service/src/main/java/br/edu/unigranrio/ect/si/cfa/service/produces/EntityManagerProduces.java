package br.edu.unigranrio.ect.si.cfa.service.produces;

import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerProduces {

    private static final String PU = "cfaPU";

    @Produces
    @ApplicationScoped
    public EntityManagerFactory createEntityManagerFactory() {
        return new HibernatePersistenceProvider().createEntityManagerFactory(PU, null);
    }


    @Produces
    @RequestScoped
    public EntityManager createEntityManger(EntityManagerFactory factory) {
        return factory != null ? factory.createEntityManager() : null;
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        em.close();
    }
}
