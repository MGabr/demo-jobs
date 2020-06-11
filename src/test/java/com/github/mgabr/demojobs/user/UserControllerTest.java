package com.github.mgabr.demojobs.user;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SeedWithDataset("users.json")
    @AssertMatchesDatasets({@AssertMatchesDataset("users.json"), @AssertMatchesDataset("new-user.json")})
    public void createShouldCreateUser() throws Exception {
        var user = new UserCreateDTO("company2@gmail.com", "password", UserRole.ROLE_COMPANY);
        var request = post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(user))
                .with(csrf());

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(value = "company1@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @SeedWithDataset("users.json")
    @AssertMatchesDataset("users.json")
    public void getShouldReturnUser() throws Exception {
        var request = get("/users/me");
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("5edd4c7c25e9eca1635a0ee7")))
                .andExpect(jsonPath("$.email", equalTo("company1@gmail.com")))
                .andExpect(jsonPath("$.role", equalTo(UserRole.ROLE_COMPANY.name())));
    }

    @Test
    @SeedWithDataset("users.json")
    @AssertMatchesDataset("users.json")
    public void getWithNoUserShouldReturnUnauthorized() throws Exception {
        var request = get("/users/me");
        this.mockMvc.perform(request).andExpect(status().isUnauthorized());
    }
}
