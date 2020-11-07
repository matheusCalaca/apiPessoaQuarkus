package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "Campo Nome não pode ser NULL")
    @NotEmpty
    private String nome;

    private String sobrenome;

    private Date dataNascimento;

    @NotNull(message = "Campo CPF não pode ser NULL")
    private String cpf;

    @NotNull(message = "Campo E-mail não pode ser NULL")
    @NotEmpty
    private String email;

    @NotNull(message = "Campo senha não pode ser NULL")
    @NotEmpty
    private String senha;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
