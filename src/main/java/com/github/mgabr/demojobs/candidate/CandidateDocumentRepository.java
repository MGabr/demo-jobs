package com.github.mgabr.demojobs.candidate;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateDocumentRepository extends MongoRepository<CandidateDocument, String> {

}
