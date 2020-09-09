package br.com.matheusCalaca.user.repository;

import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public UserPerson insertUser(UserPerson person) {
        entityManager.persist(person);
        return person;
    }

    @Transactional
    public void updateUser(UserPerson person) {
        UserPerson.update("nome = ?1,  sobrenome = ?2, email = ?3, dataNascimento = ?4 where cpf = ?5", person.getNome(), person.getSobrenome(), person.getEmail(), person.getDataNascimento(), person.getCpf());
    }

    @Transactional
    public void deleteUser(Long id) {
        UserPerson.deleteById(id);
    }

    @Transactional
    public UserPerson findUserById(Integer id) {
        return UserPerson.findById(id.longValue());
    }

    @Transactional
    public UserPerson findUserByCpf(String cpf) {
        PanacheQuery<UserPerson> panacheQuery = UserPerson.find("cpf", cpf);
        return panacheQuery.singleResult();
    }

    @Transactional
    public UserPerson findUserByEmail(String email) {
        PanacheQuery<UserPerson> panacheQuery = UserPerson.find("email", email);
        return panacheQuery.singleResult();
    }
}
