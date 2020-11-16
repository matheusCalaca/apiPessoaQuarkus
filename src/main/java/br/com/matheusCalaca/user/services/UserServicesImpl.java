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
import br.com.matheusCalaca.user.model.User;
import br.com.matheusCalaca.user.repository.UserRepository;
import br.com.matheusCalaca.user.uteis.UteisValidation;

@ApplicationScoped
public class UserServicesImpl implements UserServices {

    @Inject
    UserRepository userRepository;
    @Inject
    PBKDF2Encoder pbkdf2Encoder;


    public User insertUser(User user) {
        validUser(user);
        if (user.getRoles() == null) {
            HashSet<RoleEnum> roles = new HashSet<>();
            roles.add(RoleEnum.USER);
            user.setRoles(new ArrayList<>(roles));

        }

        user.setPassword(pbkdf2Encoder.encode(user.getPassword()));

        return userRepository.insertUser(user);
    }


    private void validUser(User user) {
        boolean isInvalidEmail = user.getEmail() == null || !UteisValidation.isValidEmail(user.getEmail());
        boolean hasCpf = user.getCpf() == null || user.getCpf().isEmpty();
        boolean hasName = user.getName() == null || user.getName().isEmpty();
        boolean IsValidBithday = user.getDateOfBirth() != null && new Date().after(user.getDateOfBirth());
        boolean isInvalidPassword = !UteisValidation.isValidPassword(user.getPassword());

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

    public User updateUser(String cpf, User user) {
        User userToUpdate = findUserByCpf(cpf);

        boolean hasSobreNome = user.getLastname() != null && !user.getLastname().isEmpty();
        boolean hasNome = user.getName() != null && !user.getName().isEmpty();
        boolean hasDataNascimento = user.getDateOfBirth() != null;

        if (hasSobreNome) {
            userToUpdate.setLastname(user.getLastname());
        }
        if (hasNome) {
            userToUpdate.setName(user.getName());
        }
        if (hasDataNascimento) {
            userToUpdate.setDateOfBirth(user.getDateOfBirth());
        }

        return userRepository.updateUser(userToUpdate);
    }

    public void deleteUser(String cpf) {
        User user = findUserByCpf(cpf);
        if (user == null) {
            throw new NotFoundException();
        }
        userRepository.deleteUser(user.id);
    }

    @Override
    public User findUserByCpfOrEmail(String cpf, String email) {
        boolean cpfIsNotEmpty = cpf != null && !cpf.isEmpty();
        boolean emailIsNotEmpty = email != null && !email.isEmpty();
        User user = null;
        try {
            if (cpfIsNotEmpty) {
                user = findUserByCpf(cpf);
            } else if (emailIsNotEmpty) {
                user = findUserByEmail(email);
            }
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    private User findUserByCpf(String cpf) {
        validaBusca(cpf);
        return userRepository.findUserByCpf(cpf);
    }

    private User findUserByEmail(String email) {
        validaBusca(email);
        return userRepository.findUserByEmail(email);
    }

    private void validaBusca(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Valor invalido para a ação!");
        }
    }
}
