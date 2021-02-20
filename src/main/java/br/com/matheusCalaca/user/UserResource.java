package br.com.matheusCalaca.user;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.matheusCalaca.user.JWT.PBKDF2Encoder;
import br.com.matheusCalaca.user.JWT.UteisToken;
import br.com.matheusCalaca.user.model.DTO.AuthRequestDto;
import br.com.matheusCalaca.user.model.DTO.AuthResponseDto;
import br.com.matheusCalaca.user.model.DTO.UserCPFReturnDto;
import br.com.matheusCalaca.user.model.DTO.UserInsertDto;
import br.com.matheusCalaca.user.model.DTO.UserReturnDto;
import br.com.matheusCalaca.user.model.DTO.UserUpdateto;
import br.com.matheusCalaca.user.model.User;
import br.com.matheusCalaca.user.model.mapper.UserMapper;
import br.com.matheusCalaca.user.services.UserServices;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Authorization", type = SecuritySchemeType.HTTP, scheme = "bearer")
@Tag(name = "User API", description = "Cadastro de usuário")
public class UserResource {

    @Inject
    UserServices userServices;
    @Inject
    PBKDF2Encoder passwordEncoder;

    @ConfigProperty(name = "br.com.matheuscalaca.quarkusjwt.jwt.duration")
    public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Obeter o token ",
            description = "Método responsável, por fazer o login na aplicação retornando o token, informar email ou cpf(identify) e a senha "
    )
    public Response login(@Valid AuthRequestDto authRequestDto) {
        User user = userServices.findUserByCpfOrEmail(authRequestDto.getIdentify(), authRequestDto.getEmail());
        if (user != null && user.getPassword().equals(passwordEncoder.encode(authRequestDto.getPassword()))) {
            try {
                long expiretedDate = UteisToken.currentTimeInSecs() + duration;
                String token = UteisToken.generateToken(user.getCpf(), user.getRoles(), expiretedDate, issuer);
                return Response.ok(new AuthResponseDto(token, expiretedDate)).build();
            } catch (Exception e) {
                return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
            }
        } else {
            return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
    }


    @POST
    @Operation(
            summary = "Criar um usuario ",
            description = "Método responsável, por criar o usuario, atualmente a data de não está funcionando passar null. Tipo de regras [USER, ADMIN]"
    )
    public Response insertUserRest(@RequestBody @Valid UserInsertDto userDTO) {
        try {
            User user = UserMapper.INSTANCE.toUser(userDTO);
            userServices.insertUser(user);
        } catch (IllegalArgumentException e) {
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }


    @PUT
    @Path("/{cpf}")
    @RolesAllowed({"ADIMIN", "USER"})
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "Atualizar os dados de um usuário",
            description = "Método responsável, por atualizar os dados do usuário, atualmente a data de não está funcionando passar null "
    )
    public Response updateUserRest(@PathParam("cpf") @NotEmpty(message = "Informe o CPF de alteração") String cpf, @Valid @RequestBody UserUpdateto userDTO) {
        try {
            User user = UserMapper.INSTANCE.toUser(userDTO);
            userServices.updateUser(cpf, user);
        } catch (IllegalArgumentException e) {

            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed({"ADIMIN"})
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "Deleta um usuario ",
            description = "Método responsável, deletar um usuario "
    )
    public void deleteUserRest(@QueryParam("cpf") String cpf) {
        userServices.deleteUser(cpf);
    }

    @GET
    @RolesAllowed({"ADIMIN", "USER"})
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "Buscar um usuario ",
            description = "Método responsável, por Buscar os dados de um  usuário, Fazer a busca por email ou cpf "
    )
    public Response findUserRest(@QueryParam("cpf") String cpf, @QueryParam("email") String email) {
        try {
            User user = userServices.findUserByCpfOrEmail(cpf, email);

            UserReturnDto userReturnDto = UserMapper.INSTANCE.toDto(user);
            return Response.ok().entity(userReturnDto).build();

        } catch (NoResultException e) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity("Cadastro Não localizado").build();
        }

    }

    @GET
    @RolesAllowed({"ADIMIN", "USER"})
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "Buscar um usuario ",
            description = "Método responsável, por Buscar os dados de um  usuário, Fazer pelo token informado "
    )
    @Path("/token")
    public Response findUserByTokenRest(@Context HttpHeaders headers) {
        try {

            String token = UteisToken.getTokenHeader(headers);
            String cpf = UteisToken.getCPFByToken(token);

            User user = userServices.findUserByCpfOrEmail(cpf, null);

            UserReturnDto userReturnDto = UserMapper.INSTANCE.toDto(user);
            return Response.ok().entity(userReturnDto).build();

        } catch (NoResultException e) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity("Não localizado usuario por token ").build();
        }

    }

    @GET
    @RolesAllowed({"ADIMIN", "USER"})
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "Retorna CPF token ",
            description = "Método responsável, por retornar o CPF "
    )
    @Path("/token/cpf")
    public Response findCPFByToken(@Context HttpHeaders headers) {
        try {

            String token = UteisToken.getTokenHeader(headers);
            String cpf = UteisToken.getCPFByToken(token);

            return Response.ok().entity(new UserCPFReturnDto(cpf)).build();

        } catch (NoResultException e) {
            return Response.status(HttpStatus.SC_NOT_FOUND).entity("Não localizado CPF ").build();
        }

    }
}
