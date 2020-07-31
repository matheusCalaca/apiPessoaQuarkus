package br.com.matheusCalaca.user.services;

import br.com.matheusCalaca.user.model.UserPerson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserServices {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void insertUser(UserPerson person){
        entityManager.persist(person);
    }
}
