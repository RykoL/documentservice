package de.rlang.documentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rlang.documentservice.auth.AuthTokenService;
import de.rlang.documentservice.helper.DTOFactory;
import de.rlang.documentservice.helper.TestConstants;
import de.rlang.documentservice.model.dto.in.CreateProjectDTO;
import de.rlang.documentservice.service.ProjectService;
import de.rlang.documentservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private DTOFactory dtoFactory;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createNewProject() throws Exception {
        CreateProjectDTO createProjectDTO = new CreateProjectDTO("TestProject");

        when(userService.getCurrentAuthenticatedUser()).thenReturn(dtoFactory.buildUser());

        // when(projectService.createProject(any(CreateProjectDTO.class))).then(new ProjectInf)

        String authToken = authTokenService.buildAuthenticationToken(TestConstants.UserUUID.toString());
        this.mockMvc.perform(
                post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + authToken)
                .content(new ObjectMapper().writeValueAsString(createProjectDTO)))
                .andExpect(status().isCreated());
    }
}
