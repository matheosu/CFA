package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.model.*;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.FlowVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;

@Path("flows")
public class FlowPath extends PathAdapter {

    @Context
    private UriInfo uriInfo;

    private final FlowService service;

    @Inject
    public FlowPath(FlowService service) {
        this.service = service;
    }

    @GET
    @Path("/{flowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("flowId") Long flowId) {
        return ok(FlowVO.class, service.find(Flow.class, flowId).orElse(null));
    }

    @GET
    @Path("users/{userId}/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response availableFlows(@PathParam("userId") Long userId) {
        Optional<User> oUser = service.find(User.class, userId);
        if (!oUser.isPresent())
            return notFound();

        User user = oUser.get();
        FlowRestriction fr = user.getFlowRestriction();
        Restriction restriction = fr.getRestriction();
        Properties response = new Properties();
        response.put(restriction.getType(), service.availableFlow(user));
        return ok(response);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response measure(@FormParam("userId") Long userId,
                            @FormParam("controllerId") Long controllerId,
                            @FormParam("measure") Float measure) {
        if(userId == null || controllerId == null)
            return notFound();

        /* Busco o usuário */
        Optional<User> oUser = service.find(User.class, userId);
        if (!oUser.isPresent())
            return notFound();

        /* Busco o controller */
        Optional<Controller> oController = service.find(Controller.class, controllerId);
        if (!oController.isPresent())
            return notFound();

        /* Crio um novo fluxo */
        Flow flow = new Flow();
        flow.setUser(oUser.get());
        flow.setController(oController.get());
        flow.setMeasure(measure); // Sempre é Litro por Minuto
        flow.setRegistrantion(Calendar.getInstance());
        service.save(flow); // Salvo o fluxo

        /* Crio a URI correta */
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(FlowPath.class, "getById");
        return Response.created(builder.build(flow.getId())).build(); /* Respondo */
    }
}
