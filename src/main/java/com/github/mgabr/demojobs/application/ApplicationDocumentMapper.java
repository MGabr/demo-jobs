package com.github.mgabr.demojobs.application;

import com.github.mgabr.demojobs.common.ObjectIdMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class, ApplicationMessageDocumentMapper.class})
public interface ApplicationDocumentMapper {

    ApplicationDocument toDocument(ApplicationCreateDTO dto);
    ApplicationDTO toDTO(ApplicationDocument document);
    List<ApplicationDTO> toDTOs(List<ApplicationDocument> documents);
}
