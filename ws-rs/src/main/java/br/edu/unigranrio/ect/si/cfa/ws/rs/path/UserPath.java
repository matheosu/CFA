package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.model.User;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.UserVO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("users")
public class UserPath extends PathAdapter {

    private final UserService userService;

    @Inject
    public UserPath(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get() {
        List<User> list = userService.list(User.class);
        List<UserVO> validObjects = list.stream().filter(u -> !Constants.USER_ADMIN_ID.equals(u.getId()))
                .map(UserVO::new).collect(Collectors.toList());
        return ok(validObjects);
    }

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("userId") Long userId) {
        User user = userService.find(User.class, userId).orElse(null);
        return ok(UserVO.class, user);
    }

}
