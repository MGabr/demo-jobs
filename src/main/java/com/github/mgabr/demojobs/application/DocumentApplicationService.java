package com.github.mgabr.demojobs.application;

import com.github.mgabr.demojobs.candidate.CandidateDocumentRepository;
import com.github.mgabr.demojobs.company.CompanyDocumentRepository;
import com.github.mgabr.demojobs.exception.NotFoundException;
import com.github.mgabr.demojobs.job.JobDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentApplicationService implements ApplicationService {

    private final Clock clock;

    private final ApplicationDocumentMapper applicationMapper;
    private final ApplicationMessageDocumentMapper messageMapper;

    private final ApplicationDocumentRepository applicationRepository;
    private final CandidateDocumentRepository candidateRepository;
    private final JobDocumentRepository jobRepository;
    private final CompanyDocumentRepository companyRepository;

    @Override
    public String create(ApplicationCreateDTO application) {
        var candidateDoc = candidateRepository
                .findById(application.getCandidateId())
                .orElseThrow(NotFoundException::new);

        var jobDoc = jobRepository
                .findById(application.getJobId())
                .orElseThrow(NotFoundException::new);;
        var companyDoc = companyRepository
                .findById(application.getCompanyId())
                .orElseThrow(NotFoundException::new);

        // TODO: job and company id comparison

        var applicationDoc = applicationMapper.toDocument(application);
        applicationDoc.setCandidateName(candidateDoc.getName());
        applicationDoc.setJobName(jobDoc.getName());
        applicationDoc.setCompanyName(companyDoc.getName());
        applicationDoc.setMessages(new ArrayList<>());

        return applicationRepository.save(applicationDoc).getId().toString();
    }

    @Override
    public List<ApplicationDTO> get(Optional<String> jobId, Optional<String> candidateId, Optional<String> companyId) {
        var exampleDoc = new ApplicationDocument();
        jobId.map(ObjectId::new).ifPresent(exampleDoc::setJobId);
        candidateId.map(ObjectId::new).ifPresent(exampleDoc::setCandidateId);
        companyId.map(ObjectId::new).ifPresent(exampleDoc::setCompanyId);
        var example = Example.of(exampleDoc);

        var applicationDocs = applicationRepository.findAll(example);
        return applicationMapper.toDTOs(applicationDocs);
    }

    @Override
    public ApplicationDTO get(String applicationId) {
        var applicationDoc = applicationRepository
                .findById(applicationId)
                .orElseThrow(NotFoundException::new);
        return applicationMapper.toDTO(applicationDoc);
    }

    @Override
    public String createMessage(String applicationId, ApplicationMessageCreateDTO message) {
        var applicationDoc = applicationRepository
                .findById(applicationId)
                .orElseThrow(NotFoundException::new);

        var messageIndex = applicationDoc.getMessages().size();

        var messageDoc = messageMapper.toDocument(message);
        messageDoc.setSentAt(LocalDateTime.now(clock));
        applicationDoc.getMessages().add(messageDoc);
        applicationRepository.save(applicationDoc);

        return String.valueOf(messageIndex);
    }

    @Override
    public List<ApplicationMessageDTO> getMessages(String applicationId) {
        var applicationDoc = applicationRepository
                .findById(applicationId)
                .orElseThrow(NotFoundException::new);
        return messageMapper.toDTOs(applicationDoc.getMessages());
    }
}
