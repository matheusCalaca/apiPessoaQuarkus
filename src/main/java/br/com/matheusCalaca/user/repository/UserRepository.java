package br.com.matheusCalaca.user.repository;

import br.com.matheusCalaca.user.model.UserPerson;

public interface UserRepository {

    public UserPerson insertUser(UserPerson person);


    public void updateUser(Long id, UserPerson person);


    public void deleteUser(Long id);


    public UserPerson findUserById(Integer id);


    public UserPerson findUserByCpf(String cpf);


    public UserPerson findUserByEmail(String email);
}
