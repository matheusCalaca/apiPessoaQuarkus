package br.com.matheusCalaca.user.model.mapper;

import br.com.matheusCalaca.user.model.DTO.UserInsertDto;
import br.com.matheusCalaca.user.model.DTO.UserReturnDto;
import br.com.matheusCalaca.user.model.DTO.UserUpdateto;
import br.com.matheusCalaca.user.model.UserPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserPerson toUser(UserInsertDto sourceDto);

    @Mapping(target = "sobrenome", source = "lastname")
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    UserPerson toUser(UserUpdateto sourceDto);

    UserReturnDto toDto(UserPerson source);
}
