package com.github.mgabr.demojobs.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDocumentMapper {

    UserDocument toDocument(UserCreateDTO dto);
}
