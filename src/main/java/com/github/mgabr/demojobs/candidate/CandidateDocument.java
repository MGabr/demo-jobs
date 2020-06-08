package com.github.mgabr.demojobs.candidate;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("candidates")
@TypeAlias("candidate")
public class CandidateDocument {

    @Id
    private ObjectId id;

    private String email;
    private String password;
    private String name;
    private String description;
    private List<WorkExperienceDocument> workExperience;
    private List<EducationDocument> education;
}
