package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;
import org.apache.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResourceImp implements UserResource {


    @GET
    @Override
    public Response list() {
        return Response.ok().encoding(UserPerson.findAll().toString()).build();
    }

    @GET
    @Path("/{id}")
    @Override
    public UserPerson get(@PathParam("id") Integer id) {
        return UserPerson.findById(id);
    }

    @POST
    @Override
    public Response add(UserPerson person) {
        return null;
    }

    @PUT
    @Path("/{id}")
    @Override
    public Response update(@PathParam("id") Integer id, UserPerson person) {
        return null;
    }

    @DELETE
    @Override
    public void delete(Integer id) {
        UserPerson.deleteById(id);
    }
}
