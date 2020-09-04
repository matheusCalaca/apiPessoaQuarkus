package br.com.matheusCalaca.user.repository;

import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository{
    @Inject
    EntityManager entityManager;

    @Transactional
    public UserPerson insertUser(UserPerson person) {
        entityManager.persist(person);
        return person;
    }

    @Transactional
    public void updateUser(Long id, UserPerson person) {
        UserPerson.update("nome = ?1, cpf = ?2, sobrenome = ?3, email = ?4, dataNascimento = ?5 where id = ?6", person.getNome(), person.getCpf(), person.getSobrenome(), person.getEmail(), person.getDataNascimento(), id);
    }

    @Transactional
    public void deleteUser(Long id) {
        UserPerson.deleteById(id);
    }

    @Transactional
    public UserPerson findUserById(Integer id) {
        UserPerson person = UserPerson.findById(id.longValue());
        return person;
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
