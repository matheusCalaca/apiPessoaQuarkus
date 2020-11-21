package br.com.matheusCalaca.user.repository;

import br.com.matheusCalaca.user.model.User;

public interface UserRepository {

    User insertUser(User user);


    User updateUser(User user);

    void deleteUser(Long id);

    User findUserByCpf(String cpf);


    User findUserByEmail(String email);
}
