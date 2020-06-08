package com.github.mgabr.demojobs.candidate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mgabr.demojobs.DemoJobsTestConfiguration;
import com.github.mgabr.demojobs.mongounit.MongoUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mongounit.AssertMatchesDataset;
import org.mongounit.AssertMatchesDatasets;
import org.mongounit.SeedWithDataset;
import org.mongounit.SeedWithDatasets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@Import(DemoJobsTestConfiguration.class)
@AutoConfigureMockMvc
@MongoUnitTest
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SeedWithDataset("candidates.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("candidates.json"), @AssertMatchesDataset("new-candidate.json")})
    public void createShouldCreateCandidate() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(new EducationDTO(
                "Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01")
        ));
        var candidate = new CandidateCreateDTO(
                "candidate3@gmail.com", "qwertz", "Candidate 3", "", workExperiences, education
        );
        var request = post("/candidates")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate));

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @SeedWithDatasets({@SeedWithDataset("candidates.json"), @SeedWithDataset("candidate.json")})
    @AssertMatchesDatasets({@AssertMatchesDataset("candidates.json"), @AssertMatchesDataset("updated-candidate.json")})
    public void updateShouldUpdateCandidate() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "Lead teams to success in multiple projects", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(
                new EducationDTO("Master Management", "WU", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2020-01-01")),
                new EducationDTO("Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01"))
        );
        var candidate = new CandidateDTO(
                "candidate3@gmail.com", "Best candidate", "", workExperiences, education
        );
        var request = put("/candidates/{candidateId}", "5ede73623134dbfff2beafb7")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate));

        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @SeedWithDataset("candidates.json")
    @AssertMatchesDataset("candidates.json")
    public void updateWithUnknownCandidateShouldReturnNotFound() throws Exception {
        var workExperiences = List.of(new WorkExperienceDTO(
                "Lead Manager", "Success Solutions", "Lead teams to success in multiple projects", LocalDate.parse("2018-01-01"), LocalDate.parse("2019-01-01")
        ));
        var education = List.of(
                new EducationDTO("Master Management", "WU", "", LocalDate.parse("2018-01-01"), LocalDate.parse("2020-01-01")),
                new EducationDTO("Bachelor Management", "WU", "", LocalDate.parse("2014-01-01"), LocalDate.parse("2017-01-01"))
        );
        var candidate = new CandidateDTO(
                "candidate3@gmail.com", "Best candidate", "", workExperiences, education
        );
        var request = put("/candidates/{candidateId}", "5ede4c3dcf29959145b1fd41")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(candidate));

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @SeedWithDataset("candidates.json")
    @AssertMatchesDataset("candidates.json")
    public void getShouldReturnCandidate() throws Exception {
        var request = get("/candidates/{candidateId}", "5edd4c76273e08fae21fac96");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Candidate 2")))
                .andExpect(jsonPath("$.workExperience", hasSize(0)))
                .andExpect(jsonPath("$.education", hasSize(1)))
                .andExpect(jsonPath("$.education[0].institution", equalTo("TU")));
    }

    @Test
    @SeedWithDataset("candidates.json")
    @AssertMatchesDataset("candidates.json")
    public void getWithUnknownCandidateShouldReturnNotFound() throws Exception {
        var request = get("/candidates/{candidateId}", "5ede4c3dcf29959145b1fd41");

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
