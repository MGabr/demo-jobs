package com.github.mgabr.demojobs.application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    String create(ApplicationCreateDTO application);

    List<ApplicationDTO> get(Optional<String> jobId, Optional<String> candidateId, Optional<String> companyId);

    ApplicationDTO get(String applicationId);

    String createMessage(String applicationId, ApplicationMessageCreateDTO message);

    List<ApplicationMessageDTO> getMessages(String applicationId);
}
