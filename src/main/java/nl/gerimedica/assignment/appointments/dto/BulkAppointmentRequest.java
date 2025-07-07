package nl.gerimedica.assignment.appointments.dto;

import java.util.List;

public record BulkAppointmentRequest(
        String patientSsn,
        String patientName,
        List<AppointmentRequest> appointments
) {

}
