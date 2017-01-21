package br.edu.unigranrio.ect.si.cfa.web.producer;

import br.edu.unigranrio.ect.si.cfa.web.annotation.RequestParam;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import java.io.Serializable;

public class RequestParamProducer implements Serializable {

    private static final long serialVersionUID = 2L;

    private final ExternalContext context;

    @Inject
    public RequestParamProducer(ExternalContext context) {
        this.context = context;
    }

    @Produces @RequestParam
    public String getRequestParameter(InjectionPoint ip) {
        String name = ip.getAnnotated().getAnnotation(RequestParam.class)
                .value();

        if ("".equals(name))
            name = ip.getMember().getName();

        return context.getRequestParameterMap().get(name);
    }


}
