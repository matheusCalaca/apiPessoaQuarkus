package br.com.matheusCalaca.user.DTO;

import java.io.Serializable;

public class AuthResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
