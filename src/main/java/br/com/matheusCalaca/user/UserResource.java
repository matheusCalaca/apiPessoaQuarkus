package br.com.matheusCalaca.user;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.services.UserServices;
import org.apache.http.HttpStatus;

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
        } catch (IllegalArgumentException e) {
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @PUT
    public Response updateUserPersonRest(@Valid UserPerson person) {
        try {
            userServices.updateUser(person);
        } catch (IllegalArgumentException e) {

            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    public void deleteUserPersonRest(@QueryParam("cpf") String cpf) {
        userServices.deleteUser(cpf);
    }

    @GET
    public Response findUserPersonRest(@QueryParam("id") Integer id, @QueryParam("cpf") String cpf, @QueryParam("email") String email) {
        UserPerson user = null;
        boolean cpfIsNotEmpty = cpf != null && !cpf.isEmpty();
        boolean emailIsNotEmpty = email != null && !email.isEmpty();
        UserPerson person = null;

        try {
            if (cpfIsNotEmpty) {
                person = userServices.findUserByCpf(cpf);
            } else if (emailIsNotEmpty) {
                person = userServices.findUserByEmail(email);
            }
        } catch (NoResultException e) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity("Cadastro Não localizado").build();
        }

        return Response.ok().entity(person).build();
    }
}
