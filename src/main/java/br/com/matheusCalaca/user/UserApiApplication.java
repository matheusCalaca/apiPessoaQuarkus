package br.com.matheusCalaca.user;


import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "User API", contact = @Contact(
                name = "Matheus Calaça",
                url = "http://matheuscalaca.com.br/",
                email = "matheusfcalaca@gmail.com"
        ), version = "1.0.1", description = "Api para fazer o cadastro de usuário. \n Para determinados serviços são nescessario o tooken JWT. \n" +
                " Existe dois tipos de usuários (USER, ADMIN)." +
                "  "))
public class UserApiApplication extends Application {
}
