package com.github.mgabr.demojobs.candidate;

public interface CandidateService {

    void upsert(CandidateDTO candidate);

    CandidateDTO get(String candidateId);
}
