package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.model.Controller;
import br.edu.unigranrio.ect.si.cfa.service.ControllerService;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.ControllerVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("controllers")
public class ControllerPath extends PathAdapter {

    @Inject
    private ControllerService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        List<Controller> list = service.list(Controller.class);
        List<ControllerVO> validObjects = list.stream()
                                          .map(ControllerVO::new)
                                          .collect(Collectors.toList());
        return ok(validObjects);
    }

    @GET
    @Path("{controllerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("controllerId") Long controllerId){
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
