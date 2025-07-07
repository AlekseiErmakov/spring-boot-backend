package nl.gerimedica.assignment.patients;

import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.patients.dto.PatientCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PatientCreateRequest patientCreateRequest) {
        return ResponseEntity.ok(patientService.createPatient(patientCreateRequest));
    }
}
