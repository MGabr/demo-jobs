package com.github.mgabr.demojobs.application;

import com.github.mgabr.demojobs.user.IdUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('CANDIDATE') && #user.id == #application.candidateId")
    public String create(@RequestBody ApplicationCreateDTO application, @AuthenticationPrincipal IdUserDetails user) {
        return applicationService.insert(application);
    }

    @GetMapping
    @PreAuthorize("(hasRole('CANDIDATE') && (#candidateId.isEmpty() || #user.id == #candidateId.get())) || " +
            "(hasRole('COMPANY') && (#companyId.isEmpty() || #user.id == #companyId.get()))")
    public List<ApplicationDTO> get(@RequestParam Optional<String> jobId,
                                    @RequestParam Optional<String> candidateId,
                                    @RequestParam Optional<String> companyId,
                                    @AuthenticationPrincipal IdUserDetails user) {

        // if request parameter for user role not set, set it explicitly
        var role = user.getAuthorities().iterator().next().getAuthority();
        var userId = Optional.of(user.getId());
        var userCandidateId = role.equals("RULE_CANDIDATE") ? userId : candidateId;
        var userCompanyId = role.equals("RULE_COMPANY") ? userId : companyId;

        return applicationService.get(jobId, userCandidateId, userCompanyId);
    }

    @GetMapping("/{applicationId}")
    @PostAuthorize("(hasRole('CANDIDATE') && #user.id == returnObject.candidateId) || " +
            "(hasRole('COMPANY') && #user.id == returnObject.companyId)")
    public ApplicationDTO get(@PathVariable String applicationId, @AuthenticationPrincipal IdUserDetails user) {
        return applicationService.get(applicationId);
    }

    @PostMapping(value = "/{applicationId}/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("(hasRole('CANDIDATE') && #user.id == @applicationService.get(#applicationId).candidateId) || " +
            "(hasRole('COMPANY') && #user.id == @applicationService.get(#applicationId).companyId)")
    public String createMessage(@PathVariable String applicationId,
                                @RequestBody ApplicationMessageCreateDTO message,
                                @AuthenticationPrincipal IdUserDetails user) {

        return applicationService.insertMessage(applicationId, user.getId(), message);
    }

    @GetMapping("/{applicationId}/messages")
    @PreAuthorize("(hasRole('CANDIDATE') && #user.id == @applicationService.get(#applicationId).candidateId) || " +
            "(hasRole('COMPANY') && #user.id == @applicationService.get(#applicationId).companyId)")
    public List<ApplicationMessageDTO> getMessages(@PathVariable String applicationId,
                                                   @AuthenticationPrincipal IdUserDetails user) {

        return applicationService.getMessages(applicationId);
    }
}
