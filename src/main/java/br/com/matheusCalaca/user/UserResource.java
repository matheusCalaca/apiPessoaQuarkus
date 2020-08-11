package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.services.UserServices;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserServices userServices;

    @GET
    public String hello() {
        return "hello";
    }

    @POST
    public void insertUserPersonRest(@RequestBody UserPerson person) {
        userServices.insertUser(person);
    }

    @PUT
    @Path("/{id}")
    public void updateUserPersonRest(@PathParam("id") Long id, @RequestBody UserPerson person) {
        userServices.updateUser(id, person);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUserPersonRest(@PathParam("id") Long id) {
        userServices.deleteUser(id);
    }
}
