package com.github.mgabr.demojobs.application;

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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("applications+.json"), @AssertMatchesDataset("new-application.json")})
    public void createShouldCreateApplication() throws Exception {
        var application = new ApplicationCreateDTO("5edd4c6c35367a32563ac42c", "5edd4c8c0645577afecb377f");
        var request = post("/applications")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(application))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createWithUnknownJobShouldReturnBadRequest() throws Exception {
        var application = new ApplicationCreateDTO("5edd4c6c35367a32563ac42c", "5ede4c3dcf29959145b1fd41");
        var request = post("/applications")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(application))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createWithAnotherCandidateShouldReturnForbidden() throws Exception {
        var application = new ApplicationCreateDTO("5edd4c6c35367a32563ac42c", "5edd4c8c0645577afecb377f");
        var request = post("/applications")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(application))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createWithCompanyShouldReturnForbidden() throws Exception {
        var application = new ApplicationCreateDTO("5edd4c6c35367a32563ac42c", "5edd4c8c0645577afecb377f");
        var request = post("/applications")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(application))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getQueryWithJobShouldReturnApplicationsOfJob() throws Exception {
        var request = get("/applications").queryParam("jobId", "5edd4c87e1ba439a39a6408c");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getQueryWithAnotherCandidateShouldReturnForbidden() throws Exception {
        var request = get("/applications")
                .queryParam("jobId", "5edd4c87e1ba439a39a6408c")
                .queryParam("candidateId", "5edd4c6c35367a32563ac42c");

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getQueryWithAnotherCompanyShouldReturnForbidden() throws Exception {
        var request = get("/applications")
                .queryParam("jobId", "5edd4c87e1ba439a39a6408c")
                .queryParam("companyId", "5edd4c6c35367a32563ac42c");

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getShouldReturnApplication() throws Exception {
        var request = get("/applications/{applicationId}", "5edd4c9889594017137b9b29");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.candidateName", equalTo("Candidate 1")))
                .andExpect(jsonPath("$.jobName", equalTo("Job 1")));
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getWithUnknownApplicationShouldReturnNotFound() throws Exception {
        var request = get("/applications/{applicationId}", "5ede4c3dcf29959145b1fd41");
        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getWithAnotherCandidateShouldReturnForbidden() throws Exception {
        var request = get("/applications/{applicationId}", "5edd4c9889594017137b9b29");
        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getWithAnotherCompanyShouldReturnForbidden() throws Exception {
        var request = get("/applications/{applicationId}", "5edd4c9889594017137b9b29");
        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDatasets({@SeedWithDataset("applications+.json"), @SeedWithDataset("application.json")})
    @AssertMatchesDatasets({
            @AssertMatchesDataset("applications+.json"), @AssertMatchesDataset("application-new-message.json")
    })
    public void createMessageShouldCreateMessage() throws Exception {
        var message = new ApplicationMessageCreateDTO("Message 1");
        var request = post("/applications/{applicationId}/messages", "5ede3c8718758becc0bb5777")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(message))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createMessageWithUnknownApplicationShouldReturnNotFound() throws Exception {
        var message = new ApplicationMessageCreateDTO("Message 1");
        var request = post("/applications/{applicationId}/messages", "5ede3c8718758becc0bb5777")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(message))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createMessageWithAnotherCandidateShouldReturnForbidden() throws Exception {
        var message = new ApplicationMessageCreateDTO("Message 1");
        var request = post("/applications/{applicationId}/messages", "5edd4c9889594017137b9b29")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(message))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createMessageWithAnotherCompanyShouldReturnForbidden() throws Exception {
        var message = new ApplicationMessageCreateDTO("Message 1");
        var request = post("/applications/{applicationId}/messages", "5edd4c9889594017137b9b29")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(message))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getMessagesShouldGetMessagesOfApplication() throws Exception {
        var request = get("/applications/{applicationId}/messages", "5edd4c9889594017137b9b29");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].message", equalTo("Message 1")))
                .andExpect(jsonPath("$[1].message", equalTo("Message 2")));
    }

    @Test
    @WithUserDetails(value = "candidate1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getMessagesWithUnknownApplicationShouldReturnNotFound() throws Exception {
        var request = get("/applications/{applicationId}/messages", "5ede4c3dcf29959145b1fd41");
        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = "candidate2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getMessagesWithAnotherCandidateShouldReturnForbidden() throws Exception {
        var request = get("/applications/{applicationId}/messages", "5edd4c9889594017137b9b29");
        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "company2@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getMessagesWithAnotherCompanyShouldReturnForbidden() throws Exception {
        var request = get("/applications/{applicationId}/messages", "5edd4c9889594017137b9b29");
        this.mockMvc.perform(request).andExpect(status().isForbidden());
    }
}
