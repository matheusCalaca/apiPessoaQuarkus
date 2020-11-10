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

        user.setSenha(pbkdf2Encoder.encode(user.getSenha()));

        return userRepository.insertUser(user);
    }

    private void validUser(User user) {
        boolean isInvalidEmail = user.getEmail() == null || !UteisValidation.validateEmail(user.getEmail());
        boolean hasCpf = user.getCpf() == null || user.getCpf().isEmpty();
        boolean hasName = user.getNome() == null || user.getNome().isEmpty();
        boolean IsValidBithday = user.getDataNascimento() != null && new Date().after(user.getDataNascimento());
        boolean hasValidPassword = user.getSenha() != null && user.getSenha().trim().isEmpty();

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
        if (hasValidPassword) {
            throw new IllegalArgumentException("Senha invalida!");
        }
    }

    public User updateUser(String cpf, User user) {
        User userToUpdate = findUserByCpf(cpf);

        boolean hasSobreNome = user.getSobrenome() != null && !user.getSobrenome().isEmpty();
        boolean hasNome = user.getNome() != null && !user.getNome().isEmpty();
        boolean hasDataNascimento = user.getDataNascimento() != null;

        if (hasSobreNome) {
            userToUpdate.setSobrenome(user.getSobrenome());
        }
        if (hasNome) {
            userToUpdate.setNome(user.getNome());
        }
        if (hasDataNascimento) {
            userToUpdate.setDataNascimento(user.getDataNascimento());
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
        User person = null;
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
