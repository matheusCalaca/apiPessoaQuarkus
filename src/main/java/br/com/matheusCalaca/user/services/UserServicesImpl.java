package br.com.matheusCalaca.user.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import br.com.matheusCalaca.user.JWT.PBKDF2Encoder;
import br.com.matheusCalaca.user.model.RoleEnum;
import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.repository.UserRepository;

@ApplicationScoped
public class UserServicesImpl implements UserServices {

    @Inject
    UserRepository userRepository;
    @Inject
    PBKDF2Encoder pbkdf2Encoder;

    //todo: mover para uma lib
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public UserPerson insertUser(UserPerson person) {
        validUser(person);
        if (person.getRoles() == null) {
            HashSet<RoleEnum> roles = new HashSet<>();
            roles.add(RoleEnum.USER);
            person.setRoles(new ArrayList<>(roles));

        }

        person.setSenha(pbkdf2Encoder.encode(person.getSenha()));

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
        if (person.getSenha() != null && person.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha invalida!");
        }
    }

    public void updateUser(UserPerson person) {
        findUserByCpf(person.getCpf());
        userRepository.updateUser(person);
    }

    public void deleteUser(String cpf) {
        UserPerson person = findUserByCpf(cpf);
        if (person == null) {
            throw new NotFoundException();
        }
        userRepository.deleteUser(person.id);
    }

    public UserPerson findUserByCpf(String cpf) {
        validaBusca(cpf);
        return userRepository.findUserByCpf(cpf);
    }

    public UserPerson findUserByEmail(String email) {
        validaBusca(email);
        return userRepository.findUserByEmail(email);
    }

    private void validaBusca(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Valor invalido para a ação!");
        }
    }
}
