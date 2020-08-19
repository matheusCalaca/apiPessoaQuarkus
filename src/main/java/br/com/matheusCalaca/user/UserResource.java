package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.services.UserServices;
import io.smallrye.mutiny.Uni;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserServices userServices;

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

    @GET
    public Response  findUserPersonRest(@QueryParam("id") Integer id, @QueryParam("cpf") String cpf) {
        UserPerson user = null;
        boolean idIsNotNull = id != null;
        boolean cpfIsNotEmpty = cpf != null &&
                !cpf.isEmpty();
        UserPerson person = null;


        if (idIsNotNull) {
            person = userServices.findUserById(id);
        } else if (cpfIsNotEmpty) {
            person = userServices.findUserByCpf(cpf);
        }
        System.out.println(person);
        return Response.ok().entity(person).build();
    }
}
