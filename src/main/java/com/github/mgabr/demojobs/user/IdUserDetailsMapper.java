package com.github.mgabr.demojobs.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class IdUserDetailsMapper {

    public UserDTO toDTO(IdUserDetails user) {
        var id = user.getId();
        var email = user.getUsername();
        var type = UserRole.valueOf(user.getAuthorities().iterator().next().getAuthority());
        return new UserDTO(id, email, type);
    }
}
