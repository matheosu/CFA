package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.model.Controller;
import br.edu.unigranrio.ect.si.cfa.service.ControllerService;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.ControllerVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("controllers")
public class ControllerPath extends PathAdapter {

    @Inject
    private ControllerService service;

    @GET
    @Path("{controllerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("controllerId") Long controllerId){
        Controller controller = service.find(Controller.class, controllerId);
        return ok(ControllerVO.class, controller);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response find(@QueryParam("uuid") String uuid) {
        Controller controller = service.findByUUID(uuid);
        return controller != null ? ok(controller.getId()) : notFound();
    }
}
