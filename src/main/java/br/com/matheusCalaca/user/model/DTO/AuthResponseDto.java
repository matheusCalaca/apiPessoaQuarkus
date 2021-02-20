package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;

public class AuthResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    private Long duration;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token, Long duration) {
        this.token = token;
        this.duration = duration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
