package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class UserServicesImpl implements UserServices {

    @Inject
    private UserRepository userRepository;

    public UserPerson insertUser(@Valid UserPerson person) {
        return userRepository.insertUser(person);
    }

    public void updateUser(Long id, UserPerson person) {
        userRepository.updateUser(id, person);
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    public UserPerson findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    public UserPerson findUserByCpf(String cpf) {
        return userRepository.findUserByCpf(cpf);
    }

    public UserPerson findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
