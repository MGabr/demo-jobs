package com.github.mgabr.demojobs.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mgabr.demojobs.DemoJobsTestConfiguration;
import com.github.mgabr.demojobs.mongounit.MongoUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mongounit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@Import(DemoJobsTestConfiguration.class)
@AutoConfigureMockMvc
@MongoUnitTest
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SeedWithDataset("applications+.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("applications+.json"), @AssertMatchesDataset("new-application.json")})
    public void createShouldCreateApplication() throws Exception {
        var application = new ApplicationCreateDTO(
                "5edd4c6c35367a32563ac42c",
                "5edd4c8c0645577afecb377f",
                "5edd4c7c25e9eca1635a0ee7"
        );
        var request = post("/applications")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(application));

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createWithUnknownJobShouldReturnNotFound() throws Exception {
        var application = new ApplicationCreateDTO(
                "5edd4c6c35367a32563ac42c",
                "5ede4c3dcf29959145b1fd41",
                "5edd4c7c25e9eca1635a0ee7"
        );
        var request = post("/applications")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(application));

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getWithJobShouldReturnApplicationsOfJob() throws Exception {
        var request = get("/applications").queryParam("jobId", "5edd4c87e1ba439a39a6408c");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
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
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getWithUnknownApplicationShouldReturnNotFound() throws Exception {
        var request = get("/applications/{applicationId}", "5ede4c3dcf29959145b1fd41");

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @SeedWithDatasets({@SeedWithDataset("applications+.json"), @SeedWithDataset("application.json")})
    @AssertMatchesDatasets({
            @AssertMatchesDataset("applications+.json"), @AssertMatchesDataset("application-new-message.json")
    })
    public void createMessageShouldCreateMessage() throws Exception {
        var message = new ApplicationMessageCreateDTO(
                "Message 1",
                "5edd4c6c35367a32563ac42c",
                "5edd4c7c25e9eca1635a0ee7"
        );
        var request = post("/applications/{applicationId}/messages", "5ede3c8718758becc0bb5777")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(message));

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void createMessageWithUnknownApplicationShouldReturnNotFound() throws Exception {
        var message = new ApplicationMessageCreateDTO(
                "Message 1",
                "5edd4c6c35367a32563ac42c",
                "5edd4c7c25e9eca1635a0ee7"
        );
        var request = post("/applications/{applicationId}/messages", "5ede4c3dcf29959145b1fd41")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(message));

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
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
    @SeedWithDataset("applications+.json")
    @AssertMatchesDataset("applications+.json")
    public void getMessagesWithUnknownApplicationShouldReturnNotFound() throws Exception {
        var request = get("/applications/{applicationId}/messages", "5ede4c3dcf29959145b1fd41");

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
