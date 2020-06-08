package com.github.mgabr.demojobs.job;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobDocumentRepository extends MongoRepository<JobDocument, String> {
}
