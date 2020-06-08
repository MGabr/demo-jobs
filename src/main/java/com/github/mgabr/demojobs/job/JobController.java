package com.github.mgabr.demojobs.job;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobController {

}
