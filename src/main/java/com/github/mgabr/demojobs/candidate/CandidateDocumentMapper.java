package com.github.mgabr.demojobs.candidate;

import com.github.mgabr.demojobs.common.ObjectIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {WorkExperienceDocumentMapper.class, EducationDocumentMapper.class, ObjectIdMapper.class})
public interface CandidateDocumentMapper {

    CandidateDocument toDocument(CandidateDTO dto);
    CandidateDTO toDTO(CandidateDocument document);
}
