package com.github.mgabr.demojobs.candidate;

import com.github.mgabr.demojobs.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentCandidateService implements CandidateService {

    private final CandidateDocumentMapper candidateMapper;

    private final CandidateDocumentRepository candidateRepository;

    @Override
    public String create(CandidateCreateDTO candidate) {
        var candidateDoc = candidateMapper.toDocument(candidate);
        return candidateRepository.save(candidateDoc).getId().toString();
    }

    @Override
    public void update(String candidateId, CandidateDTO candidate) {
        var candidateDoc = candidateRepository.findById(candidateId).orElseThrow(NotFoundException::new);
        var updatedCandidateDoc = candidateMapper.toDocument(candidate, candidateDoc);
        candidateRepository.save(updatedCandidateDoc);
    }

    @Override
    public CandidateDTO get(String candidateId) {
        var candidateDoc = candidateRepository.findById(candidateId).orElseThrow(NotFoundException::new);
        return candidateMapper.toDTO(candidateDoc);
    }
}
