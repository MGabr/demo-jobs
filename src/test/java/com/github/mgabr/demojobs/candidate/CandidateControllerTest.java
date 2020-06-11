package com.github.mgabr.demojobs.candidate;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(value = "candidate3@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("candidates+.json"), @AssertMatchesDataset("candidate.json")})
    public void createOrUpdateShouldCreateCandidate() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(new EducationDTO(
                "Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01")
        ));
        var candidate = new CandidateDTO("5edf6111a87adf2b1261c5d1", "Candidate 3", "", workExperiences, education);
        var request = put("/candidates/{candidateId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "candidate3@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDatasets({@SeedWithDataset("candidates+.json"), @SeedWithDataset("candidate.json")})
    @AssertMatchesDatasets({@AssertMatchesDataset("candidates+.json"), @AssertMatchesDataset("updated-candidate.json")})
    public void createOrUpdateShouldUpdateCandidate() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "Lead teams to success in multiple projects", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(
                new EducationDTO("Master Management", "WU", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2020-01-01")),
                new EducationDTO("Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01"))
        );
        var candidate = new CandidateDTO("5edf6111a87adf2b1261c5d1", "Best candidate", "", workExperiences, education);
        var request = put("/candidates/{candidateId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDataset("candidates+.json")
    public void createOrUpdateWithAnotherCandidateShouldReturnForbidden() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(new EducationDTO(
                "Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01")
        ));
        var candidate = new CandidateDTO("5edf6111a87adf2b1261c5d1", "Candidate 3", "", workExperiences, education);
        var request = put("/candidates/{candidateId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDataset("candidates+.json")
    public void createOrUpdateWithCompanyShouldReturnForbidden() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(new EducationDTO(
                "Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01")
        ));
        var candidate = new CandidateDTO("5edf6111a87adf2b1261c5d1", "Candidate 3", "", workExperiences, education);
        var request = put("/candidates/{candidateId}", "5edf6111a87adf2b1261c5d1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDataset("candidates+.json")
    public void getShouldReturnCandidate() throws Exception {
        var request = get("/candidates/{candidateId}", "5edf60d2b4418b0f3fb714b6");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Candidate 2")))
                .andExpect(jsonPath("$.workExperience", hasSize(0)))
                .andExpect(jsonPath("$.education", hasSize(1)))
                .andExpect(jsonPath("$.education[0].institution", equalTo("TU")));
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDataset("candidates+.json")
    public void getWithAppliedToCompanyShouldReturnCandidate() throws Exception {
        var request = get("/candidates/{candidateId}", "5edf60d2b4418b0f3fb714b6");

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Candidate 2")))
                .andExpect(jsonPath("$.workExperience", hasSize(0)))
                .andExpect(jsonPath("$.education", hasSize(1)))
                .andExpect(jsonPath("$.education[0].institution", equalTo("TU")));
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDataset("candidates+.json")
    public void getWithOtherCandidateShouldReturnForbidden() throws Exception {
        var request = get("/candidates/{candidateId}", "5edf60d2b4418b0f3fb714b6");

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("candidates+.json")
    @AssertMatchesDataset("candidates+.json")
    public void getWithNotAppliedToCompanyShouldReturnForbidden() throws Exception {
        var request = get("/candidates/{candidateId}", "5edf60d2b4418b0f3fb714b6");

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }
}
