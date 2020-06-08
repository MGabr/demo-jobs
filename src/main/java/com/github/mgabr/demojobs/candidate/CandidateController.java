package com.github.mgabr.demojobs.candidate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CandidateCreateDTO candidate) {
        return candidateService.create(candidate);
    }

    @PutMapping(value = "/{candidateId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable String candidateId, @RequestBody CandidateDTO candidate) {
        candidateService.update(candidateId, candidate);
    }

    @GetMapping("/{candidateId}")
    public CandidateDTO get(@PathVariable String candidateId) {
        return candidateService.get(candidateId);
    }
}
