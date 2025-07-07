package nl.gerimedica.assignment.appointments;

import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.hospital.HospitalService;
import nl.gerimedica.assignment.utils.HospitalUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {

    private final HospitalService hospitalService;

    /**
     * Example: {
     * "reasons": ["Checkup", "Follow-up", "X-Ray"],
     * "dates": ["2025-02-01", "2025-02-15", "2025-03-01"]
     * }
     */
    @PostMapping("/bulk-appointments")
    public ResponseEntity<List<Appointment>> createBulkAppointments(
            @RequestParam String patientName,
            @RequestParam String ssn,
            @RequestBody BulkAppointmentRequest bulkAppointmentRequest
    ) {

        HospitalUtils.recordUsage("Controller triggered bulk appointments creation");

        List<Appointment> created = hospitalService.bulkCreateAppointments(patientName, ssn, bulkAppointmentRequest);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/appointments-by-reason")
    public ResponseEntity<List<Appointment>> getAppointmentsByReason(@RequestParam String keyword) {
        List<Appointment> found = hospitalService.getAppointmentsByReason(keyword);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping("/delete-appointments")
    public ResponseEntity<String> deleteAppointmentsBySSN(@RequestParam String ssn) {
        hospitalService.deleteAppointmentsBySSN(ssn);
        return new ResponseEntity<>("Deleted all appointments for SSN: " + ssn, HttpStatus.OK);
    }

    @GetMapping("/appointments/latest")
    public ResponseEntity<Appointment> getLatestAppointment(@RequestParam String ssn) {
        Appointment latest = hospitalService.findLatestAppointmentBySSN(ssn);
        return new ResponseEntity<>(latest, HttpStatus.OK);
    }
}
