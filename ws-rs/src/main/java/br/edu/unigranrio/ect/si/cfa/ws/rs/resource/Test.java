package br.edu.unigranrio.ect.si.cfa.ws.rs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("test")
public class Test {

    public static final String JSON_MSG = "{\"message\":\"%s\"}";

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@QueryParam("text") String text) {
        return Response.ok(genericObject((String.format(JSON_MSG, text)))).build();
    }

    private GenericEntity<Object> genericObject(Object object) {
        return new GenericEntity<Object>(object){};
    }

}
