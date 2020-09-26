package br.com.matheusCalaca.user.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity(name = "USER")
public class UserPerson extends PanacheEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "Campo Nome não pode ser NULL")
    @Column(nullable = false)
    private String nome;

    private String sobrenome;

    private Date dataNascimento;

    @NotNull(message = "Campo CPF não pode ser NULL")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotNull(message = "Campo E-mail não pode ser NULL")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Campo senha não pode ser NULL")
    @Column( nullable = false)
    private String senha;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    private List<RoleEnum> roles;


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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }
}
