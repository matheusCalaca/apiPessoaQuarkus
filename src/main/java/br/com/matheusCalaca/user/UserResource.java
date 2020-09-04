package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.services.UserServices;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.validation.Valid;
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
    public Response insertUserPersonRest(@Valid UserPerson person) {
        try {
            userServices.insertUser(person);
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public void updateUserPersonRest(@PathParam("id") Long id, @Valid UserPerson person) {
        userServices.updateUser(id, person);
    }

    @DELETE
    @Path("/{id}")
    public void deleteUserPersonRest(@PathParam("id") Long id) {
        userServices.deleteUser(id);
    }

    @GET
    public Response findUserPersonRest(@QueryParam("id") Integer id, @QueryParam("cpf") String cpf, @QueryParam("email") String email) {
        UserPerson user = null;
        boolean idIsNotNull = id != null;
        boolean cpfIsNotEmpty = cpf != null && !cpf.isEmpty();
        boolean emailIsNotEmpty = email != null && !email.isEmpty();
        UserPerson person = null;


        if (idIsNotNull) {
            person = userServices.findUserById(id);
        } else if (cpfIsNotEmpty) {
            person = userServices.findUserByCpf(cpf);
        } else if (emailIsNotEmpty) {
            person = userServices.findUserByEmail(email);
        }

        return Response.ok().entity(person).build();
    }
}
