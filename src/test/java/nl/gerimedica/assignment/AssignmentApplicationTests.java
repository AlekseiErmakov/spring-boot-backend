package nl.gerimedica.assignment;

import nl.gerimedica.assignment.AssignmentApplicationTests.MyContainersConfiguration;
import nl.gerimedica.assignment.appointments.AppointmentRepository;
import nl.gerimedica.assignment.patients.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@SpringBootTest(classes = MyContainersConfiguration.class)
@Testcontainers
@WithMockUser(username = "admin", roles = "ADMIN")
public abstract class AssignmentApplicationTests {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    @AfterEach
    public void clean() {
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @TestConfiguration(proxyBeanMethods = false)
    static class MyContainersConfiguration {

        @Bean
        @ServiceConnection
        PostgreSQLContainer<?> postgreSQLContainer() {
            return new PostgreSQLContainer<>("postgres:15-alpine");
        }
    }

}

