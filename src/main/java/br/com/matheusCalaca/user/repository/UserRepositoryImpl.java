package br.com.matheusCalaca.user.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.com.matheusCalaca.user.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public User insertUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(User user) {
        User.update("nome = ?1,  sobrenome = ?2, dataNascimento = ?3 where cpf = ?4", user.getName(), user.getLastname(),  user.getDateOfBirth(), user.getCpf());
        return findUserByCpf(user.getCpf());
    }

    @Transactional
    public void deleteUser(Long id) {
        User.deleteById(id);
    }

    @Transactional
    public User findUserById(Integer id) {
        return User.findById(id.longValue());
    }

    @Transactional
    public User findUserByCpf(String cpf) {
        PanacheQuery<User> panacheQuery = User.find("cpf", cpf);
        return panacheQuery.singleResult();
    }

    @Transactional
    public User findUserByEmail(String email) {
        PanacheQuery<User> panacheQuery = User.find("email", email);
        return panacheQuery.singleResult();
    }
}
