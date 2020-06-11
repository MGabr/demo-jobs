package com.github.mgabr.demojobs.application;

import com.github.mgabr.demojobs.candidate.CandidateDocumentRepository;
import com.github.mgabr.demojobs.company.CompanyDocumentRepository;
import com.github.mgabr.demojobs.exception.BadRequestException;
import com.github.mgabr.demojobs.exception.NotFoundException;
import com.github.mgabr.demojobs.job.JobDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("applicationService")
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
    public String insert(ApplicationCreateDTO application) {

        var candidateDoc = candidateRepository
                .findById(application.getCandidateId())
                .orElseThrow(BadRequestException::new);

        var jobDoc = jobRepository
                .findById(application.getJobId())
                .orElseThrow(BadRequestException::new);

        var companyDoc = companyRepository
                .findById(jobDoc.getCompanyId().toString())
                .orElseThrow(BadRequestException::new);

        var applicationDoc = applicationMapper.toDocument(application);
        applicationDoc.setCandidateName(candidateDoc.getName());
        applicationDoc.setJobName(jobDoc.getName());
        applicationDoc.setCompanyId(companyDoc.getId());
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

        return applicationMapper.toDTOs(applicationRepository.findAll(example));
    }

    /**
     * Finds application by id. Uses request scoped cache.
     * Use for example to avoid duplicate finds by same id through find in @PreAuthorize and another find
     */
    @Cacheable(cacheNames = "applications", cacheManager = "requestScopedCacheManager")
    public ApplicationDocument getByIdCached(String applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(NotFoundException::new);
    }

    @Override
    public ApplicationDTO get(String applicationId) {
        return applicationMapper.toDTO(getByIdCached(applicationId));
    }

    @Override
    public boolean exists(String candidateId, String companyId) {

        var exampleDoc = new ApplicationDocument();
        exampleDoc.setCandidateId(new ObjectId(candidateId));
        exampleDoc.setCompanyId(new ObjectId(companyId));
        var example = Example.of(exampleDoc);

        return applicationRepository.exists(example);
    }

    @Override
    public String insertMessage(String applicationId, String userId, ApplicationMessageCreateDTO message) {
        var applicationDoc = getByIdCached(applicationId);

        var messageIndex = applicationDoc.getMessages().size();

        var messageDoc = messageMapper.toDocument(message);
        messageDoc.setSentAt(LocalDateTime.now(clock));
        messageDoc.setSentFromId(new ObjectId(userId));
        applicationDoc.getMessages().add(messageDoc);
        applicationRepository.save(applicationDoc);

        return String.valueOf(messageIndex);
    }

    @Override
    public List<ApplicationMessageDTO> getMessages(String applicationId) {
        return messageMapper.toDTOs(getByIdCached(applicationId).getMessages());
    }
}
