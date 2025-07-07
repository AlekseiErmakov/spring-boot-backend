package nl.gerimedica.assignment.appointments;

import java.time.LocalDateTime;

public record AppointmentDto(Long id, Reason reason, LocalDateTime date) {

}
