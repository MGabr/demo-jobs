package com.github.mgabr.demojobs.candidate;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EducationDocumentMapper {

    EducationDocument toDocument(EducationDTO dto, @MappingTarget EducationDocument document);

    List<EducationDocument> toDocuments(List<EducationDTO> dtos, @MappingTarget List<EducationDocument> documents);

    EducationDTO toDTO(EducationDocument document);
}
