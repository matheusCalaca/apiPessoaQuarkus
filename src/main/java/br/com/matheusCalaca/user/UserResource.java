package br.com.matheusCalaca.user;

import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface UserResource extends PanacheEntityResource<UserPerson, Integer> {
}
