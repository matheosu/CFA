package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.UserVO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("users")
public class UserPath extends PathAdapter {

    private final FlowService service;

    @Inject
    public UserPath(FlowService service) {
        this.service = service;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() {
        List<User> list = service.list(User.class);
        List<UserVO> validObjects = list.stream().filter(u -> !Constants.USER_ADMIN_ID.equals(u.getId()))
                .map(UserVO::new).collect(Collectors.toList());
        return ok(validObjects);
    }

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("userId") Long userId) {
        User user = service.find(User.class, userId).orElse(null);
        return ok(UserVO.class, user);
    }

}
