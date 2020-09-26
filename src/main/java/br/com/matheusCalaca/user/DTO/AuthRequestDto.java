package br.com.matheusCalaca.user.DTO;

import java.io.Serializable;

public class AuthRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identify;
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
}
