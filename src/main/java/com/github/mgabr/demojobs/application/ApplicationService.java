package com.github.mgabr.demojobs.application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    String insert(ApplicationCreateDTO application);

    List<ApplicationDTO> get(Optional<String> jobId, Optional<String> candidateId, Optional<String> companyId);

    ApplicationDTO get(String applicationId);

    boolean exists(String candidateId, String companyId);

    String insertMessage(String applicationId, String userId, ApplicationMessageCreateDTO message);

    List<ApplicationMessageDTO> getMessages(String applicationId);
}
