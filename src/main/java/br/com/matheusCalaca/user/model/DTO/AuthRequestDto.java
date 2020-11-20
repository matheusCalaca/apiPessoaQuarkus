package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

public class AuthRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String identify;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
