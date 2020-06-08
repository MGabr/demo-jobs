package com.github.mgabr.demojobs.candidate;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

}
