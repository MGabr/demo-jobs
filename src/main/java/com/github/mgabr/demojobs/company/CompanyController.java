package com.github.mgabr.demojobs.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CompanyCreateDTO company) {
        return companyService.create(company);
    }

    @PutMapping(value = "/{companyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable String companyId, @RequestBody CompanyDTO company) {
        companyService.update(companyId, company);
    }

    @GetMapping("/{companyId}")
    public CompanyDTO get(@PathVariable String companyId) {
        return companyService.get(companyId);
    }
}
