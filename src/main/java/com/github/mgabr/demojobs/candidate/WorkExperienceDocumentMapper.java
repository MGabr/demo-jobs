package com.github.mgabr.demojobs.candidate;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkExperienceDocumentMapper {

    WorkExperienceDocument toDocument(WorkExperienceDTO dto, @MappingTarget WorkExperienceDocument document);

    List<WorkExperienceDocument> toDocuments(List<WorkExperienceDTO> dtos,
                                             @MappingTarget List<WorkExperienceDocument> documents);

    WorkExperienceDTO toDTO(WorkExperienceDocument document);
}
