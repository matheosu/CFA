package br.edu.unigranrio.ect.si.cfa.ws.rs.path;

import br.edu.unigranrio.ect.si.cfa.commons.model.Entity;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.UserVO;
import br.edu.unigranrio.ect.si.cfa.ws.rs.vo.ValidObject;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
public class UserPath {

    @Inject
    private UserService userService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@QueryParam("email") String email) {
        return Response.ok(genericVO(UserVO.class, userService.findUserByEmail(email))).build();
    }

    private <E extends Entity, VO extends ValidObject<E>> GenericEntity<VO> genericVO(Class<VO> voClass, E entity) {
        VO vo = ValidObject.entity2Vo(voClass, entity);
        return vo != null ? new GenericEntity<VO>(vo, voClass){} : null;
    }

    private GenericEntity<Object> genericObject(Object object) {
        return new GenericEntity<Object>(object){};
    }

}
