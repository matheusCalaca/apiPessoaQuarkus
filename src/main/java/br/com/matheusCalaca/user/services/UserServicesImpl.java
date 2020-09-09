package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.repository.UserRepository;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserServicesImpl implements UserServices {

    @Inject
    UserRepository userRepository;
    //todo: mover para uma lib
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public UserPerson insertUser(UserPerson person) {
        validUser(person);
        return userRepository.insertUser(person);
    }

    private void validUser(UserPerson person) {
        if (person.getCpf() == null || person.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF invalido!");
        }

        if (person.getEmail() == null || !validateEmail(person.getEmail())) {
            throw new IllegalArgumentException("E-mail invalido!");
        }

        if (person.getNome() == null || person.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido!");
        }

        if (person.getDataNascimento() != null && new Date().after(person.getDataNascimento())) {
            throw new IllegalArgumentException("Data Nascimento invalido!");
        }
    }

    public void updateUser(UserPerson person) {
        validUser(person);
        userRepository.updateUser(person);
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
