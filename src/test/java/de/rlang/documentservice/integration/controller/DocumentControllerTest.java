package de.rlang.documentservice.integration.controller;

import de.rlang.documentservice.helper.AuthHelper;
import de.rlang.documentservice.helper.DTOFactory;
import de.rlang.documentservice.model.entity.Project;
import de.rlang.documentservice.model.entity.User;
import de.rlang.documentservice.repository.ProjectRepository;
import de.rlang.documentservice.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class DocumentControllerTest {

    @LocalServerPort
    private int port;

    private User defaultUser;
    private Project defaultProject;
    private AuthHelper authHelper;
    private final TestRestTemplate testRestTemplate;
    private String serverBaseUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Before
    public void setUp() {
        serverBaseUri = "http://localhost:" + port;
        defaultUser = DTOFactory.buildDefaultUser();
        userRepository.save(defaultUser);
        defaultProject = DTOFactory.buildProject("TestProject", defaultUser);
        projectRepository.save(defaultProject);
    }

    @After
    public void tearDown() {
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }

    public DocumentControllerTest() {
        authHelper = new AuthHelper();
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void createDocument_creates_Record_and_Uploads_File() throws IOException{

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        body.add("document", getUserFileResource());

        HttpHeaders headers = authHelper.getAuthHeader(defaultUser);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Void> responseEntity =
                testRestTemplate.exchange(
                        serverBaseUri + "/api/v1/projects/" + defaultProject.getUuid() + "/documents",
                        HttpMethod.POST,
                        request,
                        Void.class);


        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    public static Resource getUserFileResource() throws IOException {
      //todo replace tempFile with a real file
      Path tempFile = Files.createTempFile("upload-test-file", ".txt");
      Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
      System.out.println("uploading: " + tempFile);
      File file = tempFile.toFile();
      //to upload in-memory bytes use ByteArrayResource instead
      return new FileSystemResource(file);
  }
}
