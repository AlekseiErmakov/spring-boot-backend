package nl.gerimedica.assignment.appointments;

import java.util.List;

public record BulkAppointmentRequest(
        String patientSsn,
        String patientName,
        List<AppointmentRequest> appointments
) {

}
