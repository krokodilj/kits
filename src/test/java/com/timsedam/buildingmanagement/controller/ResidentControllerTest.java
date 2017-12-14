package com.timsedam.buildingmanagement.controller;

import com.sun.jndi.toolkit.url.Uri;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResidentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResidenceRepository residenceRepository;

    private static final String URL_PREFIX="/api/residents";
    private String getUserToken(String username, String password) {
        UserLoginDTO userLoginData = new UserLoginDTO(username, password);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login", userLoginData,
                String.class);
        return responseEntity.getBody();
    }

    private HttpEntity<Object> getRequestEntity(Object params, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", getUserToken(username, password));

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
        return requestEntity;
    }

    /**
     * POST request to '/api/residents' with valid UserRegisterDTO
     * Expected:  residents id and HttpStatus.CREATED
     */
    @Test
    public void registerResident(){

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "compi","compi123",
                "compi@compi.compi",null);
        ResponseEntity responseEntity=restTemplate.postForEntity(URL_PREFIX ,
                getRequestEntity(userRegisterDTO, "admin", "admin"),Long.class);
        Long id = (Long) responseEntity.getBody();
        User user = userRepository.findOne(id);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(user.getUsername(),userRegisterDTO.getUsername());
        assertEquals(user.getPassword(),userRegisterDTO.getPassword());
        assertEquals(user.getEmail(),userRegisterDTO.getEmail());

        userRepository.delete(id);
    }

    /**
     * POST request to '/api/residents' sent by unauthorised user
     * Expected:  residents id and HttpStatus.FORBIDDEN
     */
    @Test
    public void registerResidentUnauthorised(){

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "compi","compi123",
                "compi@compi.compi",null);

        ResponseEntity responseEntity=restTemplate.postForEntity(
                URL_PREFIX ,
                getRequestEntity(userRegisterDTO, "mladen", "mladen"),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }

    /**
     * POST request to '/api/residents' with unavailable username
     * Expected: error message and HttpStatus.CONFLICT
     */
    @Test
    public void regiterResidentWithUsedUsername(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "mladen","mladen123",
                "mladen@mladen.mladen",null);

        ResponseEntity responseEntity=restTemplate.postForEntity(URL_PREFIX ,
                getRequestEntity(userRegisterDTO, "admin", "admin"), String.class);

        assertEquals(HttpStatus.CONFLICT,responseEntity.getStatusCode());

    }

    /**
     * POST request to '/api/residents' with invalid dto
     * Expected: error message and HttpStatus.UNPROCESSABLE_ENTITY
     */
    @Test
    public void registerResidentInvalidDTO(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "com","3",
                "compi.compi",null);
        ResponseEntity responseEntity=restTemplate.postForEntity(URL_PREFIX ,
                getRequestEntity(userRegisterDTO, "admin", "admin"),String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,responseEntity.getStatusCode());
    }


    /**
     * PUT request to 'api/residents/{residentId}/add_to_residence/{residenceId} with valid path params
     * Expected: HttpStatus.OK
     */
    @Test
    public void setResidence(){
        String residentId = "5";
        String residenceId = "3";
        ResponseEntity responseEntity=restTemplate.exchange(
                URL_PREFIX+"/"+residentId+"/add_to_residence/"+residenceId ,
                HttpMethod.PUT,
                getRequestEntity(null, "admin", "admin"),
                Object.class);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_residence/{residenceId}
     *  to non existing resident
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void setResidenceWithInvalidResidentId() {
        String residentId = "76567";
        String residenceId = "27";

        ResponseEntity responseEntity=restTemplate.exchange(
                URL_PREFIX+"/"+residentId+"/add_to_residence/"+residenceId,
                HttpMethod.PUT,
                getRequestEntity(null,"admin","admin"),
                String.class
                );
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("User not found",responseEntity.getBody());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_residence/{residenceId}
     *  for non existing residence
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void setResidenceWithInvalidResidenceId() {
        String residentId = "5";
        String residenceId = "27343";
        ResponseEntity responseEntity=restTemplate.exchange(
                URL_PREFIX+"/"+residentId+"/add_to_residence/"+residenceId,
                HttpMethod.PUT,
                getRequestEntity(null,"admin","admin"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Residence not found",responseEntity.getBody());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_owner/{residenceId} with valid path params
     * Expected: HttpStatus.OK
     */
    @Test
    public void setResidenceOwner(){
        String residentId = "5";
        String residenceId = "3";

        ResponseEntity responseEntity=restTemplate.exchange(
                URL_PREFIX+"/"+residentId+"/add_to_residence/"+residenceId,
                HttpMethod.PUT,
                getRequestEntity(null,"admin","admin"),
                String.class);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_owner/{residenceId}
     *  for non existing residence
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void setResidenceOwnerWithInvalidResidenceId() {
        String residentId = "5";
        String residenceId = "27343";
        ResponseEntity responseEntity=restTemplate.exchange(
                URL_PREFIX+"/"+residentId+"/add_to_owner/"+residenceId,
                HttpMethod.PUT,
                getRequestEntity(null,"admin","admin"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Residence not found",responseEntity.getBody());
    }


    /**
     * PUT request to 'api/residents/{residentId}/add_to_owner/{residenceId}
     *  to non existing resident
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void setResidencOwnereWithInvalidResidentId() {
        String residentId = "76567";
        String residenceId = "27";

        ResponseEntity responseEntity=restTemplate.exchange(
                URL_PREFIX+"/"+residentId+"/add_to_owner/"+residenceId,
                HttpMethod.PUT,
                getRequestEntity(null,"admin","admin"),
                String.class
        );
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("User not found",responseEntity.getBody());
    }

}
