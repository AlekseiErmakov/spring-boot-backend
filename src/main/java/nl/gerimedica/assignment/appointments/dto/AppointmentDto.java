package nl.gerimedica.assignment.appointments.dto;

import java.time.LocalDateTime;
import nl.gerimedica.assignment.appointments.model.Reason;

public record AppointmentDto(Long id, Reason reason, LocalDateTime date) {

}
