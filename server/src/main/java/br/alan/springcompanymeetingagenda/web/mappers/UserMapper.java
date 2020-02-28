package br.alan.springcompanymeetingagenda.web.mappers;

import org.mapstruct.Mapper;
import br.alan.springcompanymeetingagenda.domain.User;
import br.alan.springcompanymeetingagenda.web.models.UserDto;

/**
 * UserMapper
 * 
 * Maps {@link UserDto} to {@link User} and vice-versa.
 */
@Mapper
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
