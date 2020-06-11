package com.github.mgabr.demojobs.candidate;

import com.github.mgabr.demojobs.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentCandidateService implements CandidateService {

    private final CandidateDocumentMapper candidateMapper;

    private final CandidateDocumentRepository candidateRepository;

    @Override
    public void upsert(CandidateDTO candidate) {
        candidateRepository.save(candidateMapper.toDocument(candidate));
    }

    @Override
    public CandidateDTO get(String candidateId) {
        return candidateRepository
                .findById(candidateId)
                .map(candidateMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }
}
