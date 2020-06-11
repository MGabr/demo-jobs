package com.github.mgabr.demojobs.candidate;

import com.github.mgabr.demojobs.common.IdController;
import com.github.mgabr.demojobs.user.IdUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController implements IdController {

    private final CandidateService candidateService;

    @PutMapping(value = "/{candidateId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CANDIDATE') && #user.id == #candidateId")
    public void createOrUpdate(@PathVariable String candidateId,
                               @RequestBody CandidateDTO candidate,
                               @AuthenticationPrincipal IdUserDetails user) {

        validateSetId(candidate, candidateId);
        candidateService.upsert(candidate);
    }

    @GetMapping("/{candidateId}")
    @PreAuthorize("hasRole('CANDIDATE') && #user.id == #candidateId || " +
            "(hasRole('COMPANY') && @applicationService.exists(#candidateId, #user.id))")
    public CandidateDTO get(@PathVariable String candidateId, @AuthenticationPrincipal IdUserDetails user) {
        return candidateService.get(candidateId);
    }


}
