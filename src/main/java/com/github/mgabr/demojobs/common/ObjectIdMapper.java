package com.github.mgabr.demojobs.common;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ObjectIdMapper {

    public ObjectId toDoc(String dto) {
        return new ObjectId(dto);
    }

    public String toDTO(ObjectId doc) {
        return doc.toString();
    }
}
