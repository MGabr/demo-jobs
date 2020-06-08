package com.github.mgabr.demojobs.candidate;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("candidates")
@TypeAlias("candidate")
public class CandidateDocument {

    @Id
    ObjectId id;

    String name;
}
