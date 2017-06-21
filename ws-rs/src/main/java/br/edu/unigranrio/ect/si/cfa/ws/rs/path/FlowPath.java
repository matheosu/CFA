package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.model.*;
import br.edu.unigranrio.ect.si.cfa.service.ControllerService;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;
import br.edu.unigranrio.ect.si.cfa.service.annotation.Transactional;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.FlowVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("flows")
public class FlowPath extends PathAdapter {

    private static final String MEASURE_AVAILABLE_RESPONSE = "%s&%2f";

    @Context
    private UriInfo uriInfo;

    private final FlowService service;
    private final ControllerService controllerService;

    @Inject
    public FlowPath(FlowService service, ControllerService controllerService) {
        this.service = service;
        this.controllerService = controllerService;
    }

    @GET
    @Path("/{flowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("flowId") Long flowId) {
        return ok(FlowVO.class, service.find(Flow.class, flowId).orElse(null));
    }

    @GET
    @Path("/available/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response availableFlows(@PathParam("userId") Long userId) {
        User user = service.find(User.class, userId).orElseThrow(NotFoundException::new);
        FlowRestriction fr = user.getFlowRestriction();
        Restriction restriction = fr.getRestriction();

        Float availableFlow = service.availableFlow(user);
        String response = String.format(MEASURE_AVAILABLE_RESPONSE, restriction.getType().toString(), availableFlow);
        return ok(response);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response measure(@FormParam("uuid") String uuid,
                            @FormParam("userId") Long userId,
                            @FormParam("measure") Float measure) {

        if(userId == null || uuid == null)
            throw new NotFoundException();

        if (measure == null)
            throw new BadRequestException("Measure");

        /* Busco o usuário */
        User user = service.find(User.class, userId).orElseThrow(NotFoundException::new);

        Float available = service.availableFlow(user);
        if (available <= 0)
            throw new BadRequestException("Unavailable Flows!");


        /* Busco o controller */
        Controller controller = controllerService.findByUUID(uuid).orElseThrow(NotFoundException::new);

        /* Crio um novo fluxo */
        Flow flow = new Flow();
        flow.setUser(user);
        flow.setController(controller);
        flow.setMeasure(measure); // Sempre é Litro por Minuto
        service.save(flow); // Salvo o fluxo

        /* Crio a URI correta */
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(FlowPath.class, "getById");
        return Response.created(builder.build(flow.getId())).build();
    }
}
