package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;

import javax.validation.Valid;

public interface UserServices  {

    public UserPerson insertUser(@Valid UserPerson person);

    public void updateUser(Long id, UserPerson person);

    public void deleteUser(Long id);

    public UserPerson findUserById(Integer id);

    public UserPerson findUserByCpf(String cpf);

    public UserPerson findUserByEmail(String email);
}
