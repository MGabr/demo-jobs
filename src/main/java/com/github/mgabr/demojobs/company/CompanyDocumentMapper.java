package com.github.mgabr.demojobs.company;

import com.github.mgabr.demojobs.common.ObjectIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface CompanyDocumentMapper {

    CompanyDocument toDocument(CompanyCreateDTO dto);
    CompanyDocument toDocument(CompanyDTO dto, @MappingTarget CompanyDocument document);
    CompanyDTO toDTO(CompanyDocument document);
}
