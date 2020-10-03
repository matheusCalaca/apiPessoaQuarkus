package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserUpdateto implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "Campo Nome não pode ser NULL")
    @NotEmpty
    private String nome;

    private String sobrenome;

    private Date dataNascimento;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}
