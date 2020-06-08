package com.github.mgabr.demojobs.job;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("jobs")
@TypeAlias("job")
public class JobDocument {

    @Id
    ObjectId id;

    String name;
}
