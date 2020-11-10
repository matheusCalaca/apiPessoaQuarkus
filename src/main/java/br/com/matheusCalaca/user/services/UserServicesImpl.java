package br.com.matheusCalaca.user.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.NotFoundException;

import br.com.matheusCalaca.user.JWT.PBKDF2Encoder;
import br.com.matheusCalaca.user.model.RoleEnum;
import br.com.matheusCalaca.user.model.UserPerson;
import br.com.matheusCalaca.user.repository.UserRepository;
import br.com.matheusCalaca.user.uteis.UteisValidation;

@ApplicationScoped
public class UserServicesImpl implements UserServices {

    @Inject
    private UserRepository userRepository;
    @Inject
    private PBKDF2Encoder pbkdf2Encoder;


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
        boolean isInvalidEmail = person.getEmail() == null || !UteisValidation.isValidEmail(person.getEmail());
        boolean hasCpf = person.getCpf() == null || person.getCpf().isEmpty();
        boolean hasName = person.getNome() == null || person.getNome().isEmpty();
        boolean IsValidBithday = person.getDataNascimento() != null && new Date().after(person.getDataNascimento());
        boolean isInvalidPassword = !UteisValidation.isValidPassword(person.getSenha());

        if (hasCpf) {
            throw new IllegalArgumentException("CPF invalido!");
        }

        if (isInvalidEmail) {
            throw new IllegalArgumentException("E-mail invalido!");
        }

        if (hasName) {
            throw new IllegalArgumentException("Nome invalido!");
        }

        if (IsValidBithday) {
            throw new IllegalArgumentException("Data Nascimento invalido!");
        }
        if (isInvalidPassword) {
            throw new IllegalArgumentException("Senha invalida!");
        }
    }

    public UserPerson updateUser(String cpf, UserPerson person) {
        UserPerson userToUpdate = findUserByCpf(cpf);

        boolean hasSobreNome = person.getSobrenome() != null && !person.getSobrenome().isEmpty();
        boolean hasNome = person.getNome() != null && !person.getNome().isEmpty();
        boolean hasDataNascimento = person.getDataNascimento() != null;

        if (hasSobreNome) {
            userToUpdate.setSobrenome(person.getSobrenome());
        }
        if (hasNome) {
            userToUpdate.setNome(person.getNome());
        }
        if (hasDataNascimento) {
            userToUpdate.setDataNascimento(person.getDataNascimento());
        }

        return userRepository.updateUser(userToUpdate);
    }

    public void deleteUser(String cpf) {
        UserPerson person = findUserByCpf(cpf);
        if (person == null) {
            throw new NotFoundException();
        }
        userRepository.deleteUser(person.id);
    }

    @Override
    public UserPerson findUserByCpfOrEmail(String cpf, String email) {
        boolean cpfIsNotEmpty = cpf != null && !cpf.isEmpty();
        boolean emailIsNotEmpty = email != null && !email.isEmpty();
        UserPerson person = null;
        try {
            if (cpfIsNotEmpty) {
                person = findUserByCpf(cpf);
            } else if (emailIsNotEmpty) {
                person = findUserByEmail(email);
            }
            return person;
        } catch (NoResultException e) {
            return null;
        }
    }

    private UserPerson findUserByCpf(String cpf) {
        validaBusca(cpf);
        return userRepository.findUserByCpf(cpf);
    }

    private UserPerson findUserByEmail(String email) {
        validaBusca(email);
        return userRepository.findUserByEmail(email);
    }

    private void validaBusca(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Valor invalido para a ação!");
        }
    }
}
