package nl.gerimedica.assignment.appointments;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import nl.gerimedica.assignment.AssignmentApplicationTests;
import nl.gerimedica.assignment.appointments.dto.AppointmentRequest;
import nl.gerimedica.assignment.appointments.dto.BulkAppointmentRequest;
import nl.gerimedica.assignment.appointments.model.Reason;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppointmentControllerTest extends AssignmentApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBulkAppointments_shouldReturnOk() throws Exception {
        BulkAppointmentRequest request = new BulkAppointmentRequest(
                "123-45-6789",
                "John Doe",
                List.of(
                        new AppointmentRequest(LocalDateTime.now().plusDays(1), Reason.CHECKUP),
                        new AppointmentRequest(LocalDateTime.now().plusDays(7), Reason.FOLLOW_UP)
                )
        );

        mockMvc.perform(post("/appointments/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAppointmentsByReason_shouldReturnOk() throws Exception {
        BulkAppointmentRequest request = new BulkAppointmentRequest(
                "123-45-6789",
                "John Doe",
                List.of(
                        new AppointmentRequest(LocalDateTime.now().plusDays(1), Reason.CHECKUP),
                        new AppointmentRequest(LocalDateTime.now().plusDays(7), Reason.FOLLOW_UP)
                )
        );

        mockMvc.perform(post("/appointments/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        mockMvc.perform(get("/appointments/by-reason")
                        .param("reason", "CHECKUP")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].reason").value(Reason.CHECKUP.getValue()));
    }

    @Test
    void deleteAppointmentsBySSN_shouldReturnOk() throws Exception {
        BulkAppointmentRequest request = new BulkAppointmentRequest(
                "123-45-6789",
                "John Doe",
                List.of(
                        new AppointmentRequest(LocalDateTime.now().plusDays(1), Reason.CHECKUP),
                        new AppointmentRequest(LocalDateTime.now().plusDays(7), Reason.FOLLOW_UP)
                )
        );

        mockMvc.perform(post("/appointments/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        mockMvc.perform(delete("/appointments")
                        .param("ssn", "123-45-6789"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted all appointments for SSN: 123-45-6789"));
    }

    @Test
    void getLatestAppointment_shouldReturnOk() throws Exception {
        BulkAppointmentRequest request = new BulkAppointmentRequest(
                "123-45-6789",
                "John Doe",
                List.of(
                        new AppointmentRequest(LocalDateTime.now().plusDays(1), Reason.CHECKUP),
                        new AppointmentRequest(LocalDateTime.now().plusDays(7), Reason.FOLLOW_UP)
                )
        );

        mockMvc.perform(post("/appointments/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        mockMvc.perform(get("/appointments/latest")
                        .param("ssn", "123-45-6789")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reason").value(Reason.FOLLOW_UP.getValue()));
    }


    @Test
    void getLatestAppointment_withNonExistentSSN_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/appointments/latest")
                        .param("ssn", "999-99-9999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}