package nl.gerimedica.assignment.patients;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.gerimedica.assignment.AssignmentApplicationTests;
import nl.gerimedica.assignment.patients.dto.PatientCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class PatientControllerTest extends AssignmentApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPatient_shouldReturnOk() throws Exception {
        PatientCreateRequest request = new PatientCreateRequest("John Doe", "123-45-6789");

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    void createPatient_twice_shouldReturn_Bad_Request() throws Exception {
        PatientCreateRequest request = new PatientCreateRequest("Jane Smith", "987-65-4321");

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNumber());

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Patient with SSN 987-65-4321 already exists"));
    }


}