package br.com.matheusCalaca.user.model.DTO;

import java.io.Serializable;
import java.util.Date;

public class UserUpdateto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String lastname;

    private Date dateOfBirth;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


}
