package nl.gerimedica.assignment.appointments;

import java.time.LocalDate;
import java.util.List;

public record BulkAppointmentRequest(
        List<Reason> reasons,
        List<LocalDate> dates
) {

}
