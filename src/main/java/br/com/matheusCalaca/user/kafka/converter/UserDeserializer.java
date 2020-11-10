package br.com.matheusCalaca.user.kafka.converter;

import br.com.matheusCalaca.user.model.User;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class UserDeserializer extends JsonbDeserializer<User> {

    public UserDeserializer() {
        super(User.class);
    }
}
