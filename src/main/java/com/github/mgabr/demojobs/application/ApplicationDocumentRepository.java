package com.github.mgabr.demojobs.application;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationDocumentRepository extends MongoRepository<ApplicationDocument, String> {

}
