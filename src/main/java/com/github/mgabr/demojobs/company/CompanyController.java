package com.github.mgabr.demojobs.company;

import com.github.mgabr.demojobs.common.IdController;
import com.github.mgabr.demojobs.user.IdUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController implements IdController {

    private final CompanyService companyService;

    @PutMapping(value = "/{companyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('COMPANY') && #user.id == #companyId")
    public void createOrUpdate(@PathVariable String companyId,
                               @RequestBody CompanyDTO company,
                               @AuthenticationPrincipal IdUserDetails user) {

        validateSetId(company, companyId);
        companyService.upsert(company);
    }

    @GetMapping("/{companyId}")
    public CompanyDTO get(@PathVariable String companyId) {
        return companyService.get(companyId);
    }
}
