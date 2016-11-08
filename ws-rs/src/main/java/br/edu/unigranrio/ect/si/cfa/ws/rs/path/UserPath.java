package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.UserVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
public class UserPath extends PathAdapter {

    @Inject
    private UserService userService;

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("userId") Long userId) {
        User user = userService.find(User.class, userId);
        return ok(UserVO.class, user);
    }

}
