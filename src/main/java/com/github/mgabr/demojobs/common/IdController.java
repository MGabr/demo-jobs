package com.github.mgabr.demojobs.common;

import com.github.mgabr.demojobs.exception.ConflictException;

public interface IdController {

    default void validateSetId(IdDTO dto, String id) {
        if (dto.getId() == null) {
            dto.setId(id);
        } else if (!id.equals(dto.getId())) {
            throw new ConflictException();
        }
    }
}
