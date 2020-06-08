package com.github.mgabr.demojobs.application;

import com.github.mgabr.demojobs.common.ObjectIdMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface ApplicationMessageDocumentMapper {

    ApplicationMessageDocument toDocument(ApplicationMessageCreateDTO dto);
    ApplicationMessageDTO toDTO(ApplicationMessageDocument document);
    List<ApplicationMessageDTO> toDTOs(List<ApplicationMessageDocument> documents);
}
