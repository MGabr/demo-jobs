package com.github.mgabr.demojobs.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/applications", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody ApplicationCreateDTO application) {
        return applicationService.create(application);
    }

    @GetMapping
    public List<ApplicationDTO> get(
            @RequestParam Optional<String> jobId,
            @RequestParam Optional<String> candidateId,
            @RequestParam Optional<String> companyId
    ) {
        return applicationService.get(jobId, candidateId, companyId);
    }

    @GetMapping("/{applicationId}")
    public ApplicationDTO get(@PathVariable String applicationId) {
        return applicationService.get(applicationId);
    }

    @PostMapping(value = "/{applicationId}/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String createMessage(@PathVariable String applicationId, @RequestBody ApplicationMessageCreateDTO message) {
        return applicationService.createMessage(applicationId, message);
    }

    @GetMapping("/{applicationId}/messages")
    public List<ApplicationMessageDTO> getMessages(@PathVariable String applicationId) {
        return applicationService.getMessages(applicationId);
    }
}
