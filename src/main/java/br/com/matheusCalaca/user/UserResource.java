package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.services.UserServices;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    public void insertUserPersonRest(UserPerson person) {
        userServices.insertUser(person);
    }
}
