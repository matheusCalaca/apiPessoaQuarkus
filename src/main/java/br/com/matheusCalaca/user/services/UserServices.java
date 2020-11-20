package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.User;

import javax.validation.Valid;

public interface UserServices {

    User insertUser(@Valid User user);

    User updateUser(String cpf, User user);

    void deleteUser(String  cpf);

    User findUserByCpfOrEmail(String cpf, String email);

}
