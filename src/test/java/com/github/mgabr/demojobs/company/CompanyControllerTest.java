package com.github.mgabr.demojobs.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mgabr.demojobs.DemoJobsTestConfiguration;
import com.github.mgabr.demojobs.fixes.mongounit.MongoUnitTest;
import com.github.mgabr.demojobs.fixes.security.WithSecurityContextTestExecutionListener;
import com.github.mgabr.demojobs.fixes.security.WithUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mongounit.AssertMatchesDataset;
import org.mongounit.AssertMatchesDatasets;
import org.mongounit.SeedWithDataset;
import org.mongounit.SeedWithDatasets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = DemoJobsTestConfiguration.class)
@AutoConfigureMockMvc
@MongoUnitTest
@TestExecutionListeners(  // fixed version of @WithUserDetails
        value = WithSecurityContextTestExecutionListener.class,
        mergeMode = MergeMode.MERGE_WITH_DEFAULTS
)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(value = "company3@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("companies+.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("companies+.json"), @AssertMatchesDataset("company.json")})
    public void createOrUpdateShouldCreateCompany() throws Exception {
        var company = new CompanyDTO("5edf6111a87adf2b1261c5d1", "Company 3", "");
        var request = put("/companies/{companyId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "company3@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDatasets({@SeedWithDataset("companies+.json"), @SeedWithDataset("company.json")})
    @AssertMatchesDatasets({@AssertMatchesDataset("companies+.json"), @AssertMatchesDataset("updated-company.json")})
    public void createOrUpdateShouldUpdateCompany() throws Exception {
        var company = new CompanyDTO("5edf6111a87adf2b1261c5d1", "Best company", "We are the best");
        var request = put("/companies/{companyId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "company2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("companies+.json")
    @AssertMatchesDataset("companies+.json")
    public void createOrUpdateWithAnotherCompanyShouldReturnForbidden() throws Exception {
        var company = new CompanyDTO("5edf6111a87adf2b1261c5d1", "Company 3", "");
        var request = put("/companies/{companyId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("companies+.json")
    @AssertMatchesDataset("companies+.json")
    public void createOrUpdateWithCandidateShouldReturnForbidden() throws Exception {
        var company = new CompanyDTO("5edf6111a87adf2b1261c5d1", "Company 3", "");
        var request = put("/companies/{companyId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    @SeedWithDataset("companies+.json")
    @AssertMatchesDataset("companies+.json")
    public void getShouldReturnCompany() throws Exception {
        var request = get("/companies/{companyId}", "5edf60ec0004080aba7f7267");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Company 1")));
    }

    @Test
    @WithMockUser
    @SeedWithDataset("companies+.json")
    @AssertMatchesDataset("companies+.json")
    public void getWithUnknownCompanyShouldReturnNotFound() throws Exception {
        var request = get("/companies/{companyId}", "5edf6111a87adf2b1261c5d1");
        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
