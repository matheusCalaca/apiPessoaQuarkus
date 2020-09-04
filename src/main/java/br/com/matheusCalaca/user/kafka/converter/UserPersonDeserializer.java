package br.com.matheusCalaca.user.kafka.converter;

import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class UserPersonDeserializer extends JsonbDeserializer<UserPerson> {

    public UserPersonDeserializer() {
        super(UserPerson.class);
    }
}
