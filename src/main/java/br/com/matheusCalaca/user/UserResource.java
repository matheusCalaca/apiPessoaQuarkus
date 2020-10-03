package br.com.matheusCalaca.user;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import br.com.matheusCalaca.user.JWT.PBKDF2Encoder;
import br.com.matheusCalaca.user.JWT.TokenUteis;
import br.com.matheusCalaca.user.model.DTO.AuthRequestDto;
import br.com.matheusCalaca.user.model.DTO.AuthResponseDto;
import br.com.matheusCalaca.user.model.DTO.UserInsertDto;
import br.com.matheusCalaca.user.model.DTO.UserReturnDto;
import br.com.matheusCalaca.user.model.DTO.UserUpdateto;
import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.model.mapper.UserMapper;
import br.com.matheusCalaca.user.services.UserServices;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserServices userServices;
    @Inject
    PBKDF2Encoder passwordEncoder;

    @Inject
    UserMapper userMapper;

    @ConfigProperty(name = "br.com.matheuscalaca.quarkusjwt.jwt.duration")
    public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    //todo: não exibir a senha
    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid AuthRequestDto authRequestDto) {
        UserPerson person = userServices.findUserByCpf(authRequestDto.getIdentify());
        if (person != null && person.getSenha().equals(passwordEncoder.encode(authRequestDto.getPassword()))) {
            try {
                String token = TokenUteis.generateToken(person.getNome(), person.getRoles(), duration, issuer);
                return Response.ok(new AuthResponseDto(token)).build();
            } catch (Exception e) {
                return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
            }
        } else {
            return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
    }


    @POST
    @RolesAllowed({"ADIMIN", "USER"})
    public Response insertUserPersonRest(@RequestBody @Valid UserInsertDto userDTO) {
        try {
            UserPerson user = userMapper.toUser(userDTO);
            userServices.insertUser(user);
        } catch (IllegalArgumentException e) {
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    //todo: não permitir a alteração da senha
    @PUT
    @RolesAllowed({"ADIMIN", "USER"})
    public Response updateUserPersonRest(@Valid @RequestBody UserUpdateto userDTO) {
        try {
            UserPerson user = userMapper.toUser(userDTO);
            userServices.updateUser(user);
        } catch (IllegalArgumentException e) {

            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed({"ADIMIN"})
    public void deleteUserPersonRest(@QueryParam("cpf") String cpf) {
        userServices.deleteUser(cpf);
    }

    @GET
    @RolesAllowed({"ADIMIN", "USER"})
    public Response findUserPersonRest(@QueryParam("cpf") String cpf, @QueryParam("email") String email) {
        //todo: mover para o service regra de negocio
        boolean cpfIsNotEmpty = cpf != null && !cpf.isEmpty();
        boolean emailIsNotEmpty = email != null && !email.isEmpty();
        UserPerson person = null;

        try {
            //todo: mover para o service regra de negocio
            if (cpfIsNotEmpty) {
                person = userServices.findUserByCpf(cpf);
            } else if (emailIsNotEmpty) {
                person = userServices.findUserByEmail(email);
            }

            UserReturnDto userReturnDto = userMapper.toDto(person);
            return Response.ok().entity(userReturnDto).build();

        } catch (NoResultException e) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity("Cadastro Não localizado").build();
        }

    }
}
