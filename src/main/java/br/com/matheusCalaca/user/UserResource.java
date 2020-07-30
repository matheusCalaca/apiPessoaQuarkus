package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertUserPersonRest( UserPerson person) {
        System.out.println(person.toString());
    }
}
