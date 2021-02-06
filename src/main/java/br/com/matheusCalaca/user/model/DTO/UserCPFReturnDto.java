package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;
import java.util.Date;

public class UserCPFReturnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserCPFReturnDto(String cpf) {
        this.cpf = cpf;
    }

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
