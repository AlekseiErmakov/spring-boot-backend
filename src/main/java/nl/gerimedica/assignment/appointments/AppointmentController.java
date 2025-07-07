package nl.gerimedica.assignment.appointments;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.hospital.HospitalService;
import nl.gerimedica.assignment.utils.HospitalUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final HospitalService hospitalService;
    private final AppointmentService appointmentService;

    /**
     * Example: { "patientName": "John Doe", "patientSsn": "123-45-6789", "appointments": [{"reason": "Checkup", "date": "2025-02-01"}, {"reason": "Follow-up", "date": "2025-02-15"}] }
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<AppointmentDto>> createBulkAppointments(@RequestBody BulkAppointmentRequest request) {

        HospitalUtils.recordUsage("Controller triggered bulk appointments creation");

        return ResponseEntity.ok(hospitalService.bulkCreateAppointments(request));
    }

    @GetMapping("/by-reason")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByReason(@RequestParam Reason reason) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByReason(reason));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAppointmentsBySSN(@RequestParam String ssn) {
        hospitalService.deleteAppointmentsBySSN(ssn);
        return ResponseEntity.ok("Deleted all appointments for SSN: " + ssn);
    }

    @GetMapping("/latest")
    public ResponseEntity<AppointmentDto> getLatestAppointment(@RequestParam String ssn) {
        return ResponseEntity.ok(appointmentService.getLatestAppointmentBySsn(ssn));
    }
}
