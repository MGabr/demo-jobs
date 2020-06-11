package com.github.mgabr.demojobs.company;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("companies")
@TypeAlias("company")
public class CompanyDocument {

    @Id
    private ObjectId id;

    private String name;
    private String description;
}
