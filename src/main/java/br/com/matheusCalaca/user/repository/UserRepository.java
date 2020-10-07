package br.com.matheusCalaca.user.repository;

import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface UserRepository {

    UserPerson insertUser(UserPerson person);


    void updateUser(UserPerson person);


    void deleteUser(Long id);


    UserPerson findUserById(Integer id);


    UserPerson findUserByCpf(String cpf);


    UserPerson findUserByEmail(String email);
}
