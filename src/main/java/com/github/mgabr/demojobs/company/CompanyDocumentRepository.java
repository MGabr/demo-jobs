package com.github.mgabr.demojobs.company;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyDocumentRepository extends MongoRepository<CompanyDocument, String> {
}
