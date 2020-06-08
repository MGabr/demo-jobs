package com.github.mgabr.demojobs.candidate;

public interface CandidateService {

    String create(CandidateCreateDTO candidate);

    void update(String candidateId, CandidateDTO candidate);

    CandidateDTO get(String candidateId);
}
