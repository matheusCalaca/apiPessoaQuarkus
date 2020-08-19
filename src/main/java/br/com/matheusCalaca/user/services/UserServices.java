package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserServices {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void insertUser(UserPerson person) {
        entityManager.persist(person);
    }

    @Transactional
    public void updateUser(Long id, UserPerson person) {
        UserPerson.update("nome = ?1, cpf = ?2, sobrenome = ?3 where id = ?4", person.getNome(), person.getCpf(), person.getSobrenome(), id);
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
        PanacheQuery<UserPerson> panacheQuery =UserPerson.find("cpf", cpf);
        return panacheQuery.singleResult();
    }
}
