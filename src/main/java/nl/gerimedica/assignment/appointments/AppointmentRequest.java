package nl.gerimedica.assignment.appointments;

import java.time.LocalDateTime;

public record AppointmentRequest(
        LocalDateTime date,
        Reason reason) {

}
