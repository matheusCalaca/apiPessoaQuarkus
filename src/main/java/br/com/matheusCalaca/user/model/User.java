package br.com.matheusCalaca.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "USUARIO")
public class User extends PanacheEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "Campo Nome não pode ser NULL")
    @Column(nullable = false)
    private String name;

    private String lastname;

    private Date dateOfBirth;

    @NotNull(message = "Campo CPF não pode ser NULL")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotNull(message = "Campo E-mail não pode ser NULL")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Campo senha não pode ser NULL")
    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    private List<RoleEnum> roles;


    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String sobrenome) {
        this.lastname = sobrenome;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dataNascimento) {
        this.dateOfBirth = dataNascimento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }
}
