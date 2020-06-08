package com.github.mgabr.demojobs.application;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;

@Data
@TypeAlias("appliationMessage")
public class ApplicationMessageDocument {

    private String message;
    private ObjectId sentFromId;
    private ObjectId sentToId;
    private LocalDateTime sentAt;
}
