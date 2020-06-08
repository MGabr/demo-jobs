package com.github.mgabr.demojobs.company;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {
}
