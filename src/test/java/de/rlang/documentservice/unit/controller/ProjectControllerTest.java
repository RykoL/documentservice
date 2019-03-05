package de.rlang.documentservice.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rlang.documentservice.controller.ProjectController;
import de.rlang.documentservice.helper.TestConstants;
import de.rlang.documentservice.model.dto.out.ProjectInformationDTO;
import de.rlang.documentservice.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectController.class, secure = false)
public class ProjectControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProjectService projectService;

    @Test
    public void getProjectInformation_returns_project_information_and_status_OK() throws Exception {
        MvcResult mvcResult = mvc.perform(
                get("/api/v1/projects/" + TestConstants.NULL_UUID.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        ProjectInformationDTO projectInformationDTO =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectInformationDTO.class);

        assertThat(projectInformationDTO.getUuid(), is(TestConstants.NULL_UUID));
    }
}
