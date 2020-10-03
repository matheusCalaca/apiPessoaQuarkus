package br.com.matheusCalaca.user.model.mapper;

import br.com.matheusCalaca.user.model.DTO.UserInsertDto;
import br.com.matheusCalaca.user.model.DTO.UserReturnDto;
import br.com.matheusCalaca.user.model.DTO.UserUpdateto;
import br.com.matheusCalaca.user.model.UserPerson;
import io.quarkus.security.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    UserPerson toUser(UserInsertDto sourceDto);

    UserPerson toUser(UserUpdateto sourceDto);

    UserReturnDto toDto(UserPerson source);
}
