package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;

import javax.validation.Valid;

public interface UserServices {

    UserPerson insertUser(@Valid UserPerson person);

    UserPerson updateUser(String cpf, UserPerson person);

    void deleteUser(String  cpf);

    UserPerson findUserByCpfOrEmail(String cpf, String email);

}
