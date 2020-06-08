package com.github.mgabr.demojobs.application;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("applications")
@TypeAlias("application")
public class ApplicationDocument {

    @Id
    private ObjectId id;

    @Indexed
    private ObjectId candidateId;

    @Indexed
    private ObjectId jobId;

    @Indexed
    private ObjectId companyId;

    private String candidateName;
    private String jobName;
    private String companyName;
    private List<ApplicationMessageDocument> messages;
}
