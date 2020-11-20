package br.com.matheusCalaca.user.model.mapper;

import br.com.matheusCalaca.user.model.DTO.UserInsertDto;
import br.com.matheusCalaca.user.model.DTO.UserReturnDto;
import br.com.matheusCalaca.user.model.DTO.UserUpdateto;
import br.com.matheusCalaca.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );


    @Mapping(target = "lastname", source = "lastName")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(UserInsertDto sourceDto);

    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    User toUser(UserUpdateto sourceDto);

    UserReturnDto toDto(User source);
}
