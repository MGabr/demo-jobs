package com.github.mgabr.demojobs.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {

    Optional<UserDocument> findByEmail(String email);
}
