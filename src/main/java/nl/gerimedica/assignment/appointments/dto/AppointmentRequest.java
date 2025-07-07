package nl.gerimedica.assignment.appointments.dto;

import java.time.LocalDateTime;
import nl.gerimedica.assignment.appointments.model.Reason;

public record AppointmentRequest(
        LocalDateTime date,
        Reason reason) {

}
