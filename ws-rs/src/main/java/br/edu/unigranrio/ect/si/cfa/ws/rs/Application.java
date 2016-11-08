package br.edu.unigranrio.ect.si.cfa.ws.rs;

import br.edu.unigranrio.ect.si.cfa.ws.rs.resource.Test;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("cfa")
public class Application extends ResourceConfig {

    public Application() {
        register(Test.class);
    }
}
