package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;

import javax.validation.Valid;

public interface UserServices {

    UserPerson insertUser(@Valid UserPerson person);

    void updateUser(UserPerson person);

    void deleteUser(Long id);

    UserPerson findUserById(Integer id);

    UserPerson findUserByCpf(String cpf);

    UserPerson findUserByEmail(String email);
}
