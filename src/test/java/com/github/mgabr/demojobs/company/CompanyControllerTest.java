package com.github.mgabr.demojobs.company;

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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@Import(DemoJobsTestConfiguration.class)
@AutoConfigureMockMvc
@MongoUnitTest
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SeedWithDataset("companies.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("companies.json"), @AssertMatchesDataset("new-company.json")})
    public void createShouldCreateCompany() throws Exception {
        var company = new CompanyCreateDTO("company3@gmail.com", "pass", "Company 3", "");
        var request = post("/companies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company));

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @SeedWithDatasets({@SeedWithDataset("companies.json"), @SeedWithDataset("company.json")})
    @AssertMatchesDatasets({@AssertMatchesDataset("companies.json"), @AssertMatchesDataset("updated-company.json")})
    public void updateShouldUpdateCompany() throws Exception {
        var company = new CompanyDTO("company3@gmail.com", "Best company", "We are the best");
        var request = put("/companies/{companyId}", "5ede94835d6aa74ab2851cc2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company));

        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @SeedWithDataset("companies.json")
    @AssertMatchesDataset("companies.json")
    public void updateWithUnknownCompanyShouldReturnNotFound() throws Exception {
        var company = new CompanyDTO("company3@gmail.com", "Best company", "We are the best");
        var request = put("/companies/{companyId}", "5ede94835d6aa74ab2851cc2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(company));

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @SeedWithDataset("companies.json")
    @AssertMatchesDataset("companies.json")
    public void getShouldReturnCompany() throws Exception {
        var request = get("/companies/{companyId}", "5edd4c7c25e9eca1635a0ee7");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Company 1")));
    }

    @Test
    @SeedWithDataset("companies.json")
    @AssertMatchesDataset("companies.json")
    public void getWithUnknownCompanyShouldReturnNotFound() throws Exception {
        var request = get("/companies/{companyId}", "5ede4c3dcf29959145b1fd41");

        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
