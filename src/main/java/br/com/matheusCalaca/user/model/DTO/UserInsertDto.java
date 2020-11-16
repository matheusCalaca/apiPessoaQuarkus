package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;
import java.util.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "Campo Nome não pode ser NULL")
    @NotEmpty
    private String name;

    private String lastName;

    @JsonbDateFormat("yyyy-MM-dd")
    private Date dateOfBirth;

    @NotNull(message = "Campo CPF não pode ser NULL")
    private String cpf;

    @NotNull(message = "Campo E-mail não pode ser NULL")
    @NotEmpty
    private String email;

    @NotNull(message = "Campo senha não pode ser NULL")
    @NotEmpty
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
