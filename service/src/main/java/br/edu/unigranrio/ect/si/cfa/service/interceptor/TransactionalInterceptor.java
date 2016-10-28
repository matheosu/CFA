package br.edu.unigranrio.ect.si.cfa.service.interceptor;


import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.service.exception.ApplicationException;
import br.edu.unigranrio.ect.si.cfa.service.exception.ServiceExeption;
import br.edu.unigranrio.ect.si.cfa.service.exception.TransactionalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.Application;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Transactional
@Interceptor
public class TransactionalInterceptor implements Serializable {

    private static final long serialVersionUID = 4781174023316476560L;

    private static final Logger logger = LoggerFactory.getLogger(TransactionalInterceptor.class);

    @Inject
    private EntityManager em;

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        boolean error = false;

        EntityTransaction transaction = em.getTransaction();
        try {
            if (!transaction.isActive()) {
                logger.info("Transaction Begin");
                transaction.begin();
            }

            return context.proceed();
        } catch (ServiceExeption | ApplicationException se) {
            error = true;
            throw se;
        } catch (Exception e) {
            error = true;
            throw new TransactionalException("Transactional Error ", e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                if (!error) {
                    transaction.commit();
                    logger.info("Transaction Commit");
                } else {
                    transaction.rollback();
                    logger.error("Transaction Rollback");
                }
            }
        }
    }

}
