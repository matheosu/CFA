package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.model.Controller;
import br.edu.unigranrio.ect.si.cfa.model.Flow;
import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.FlowVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Calendar;

@Path("flows")
public class FlowPath extends PathAdapter {

    @Inject
    private FlowService service;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{flowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("flowId") Long flowId) {
        Flow flow = service.find(Flow.class, flowId);
        return ok(FlowVO.class, flow);
    }

    @GET
    @Path("/{userId}/available")
    @Produces(MediaType.TEXT_PLAIN)
    public Response availableFlows(@PathParam("userId") Long userId) {
        return ok(service.availableFlow(userId));
    }

    @POST
    @Transactional
    @Path("/{userId}/controller/{controllerId}")
    public Response measure(@PathParam("userId") Long userId,
                            @PathParam("controllerId") Long controllerId,
                            Float measure) {

        /* Busco o usuário */
        User user = service.find(User.class, userId);
        if (user == null) /* Valido se existe*/
            return notFound();

        /* Busco o controller */
        Controller controller = service.find(Controller.class, controllerId);
        if (controller == null) /* Valido se existe*/
            return notFound();

        /* Crio um novo fluxo */
        Flow flow = new Flow();
        flow.setUser(user);
        flow.setController(controller);
        flow.setMeasure(measure); // Sempre é Litro por Minuto
        flow.setRegistrantion(Calendar.getInstance());
        service.save(flow); // Salvo o fluxo

        /* Crio a URI correta */
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(flow.getId()));
        return Response.created(builder.build()).build(); /* Respondo */
    }
}
