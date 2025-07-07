package nl.gerimedica.assignment.patients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public Patient getPatient(String patientName, String ssn) {
        //add log but with optional approach
        // Use Optional to handle the case where the patient might not be found
        return patientRepository.findBySsn(ssn)
                .orElseGet(() -> patientRepository.save(new Patient(patientName, ssn)));
//        if (found == null) {
//            log.info("Creating new patient with SSN: {}", ssn);
//            found = new Patient(patientName, ssn);
//            savePatient(found);
//        } else {
//            log.info("Existing patient found, SSN: {}", found.ssn);
//        }
    }

    @Transactional(readOnly = true)
    public Patient findPatientBySsn(String ssn) {
        // Use Optional to handle the case where the patient might not be found
        return patientRepository.findBySsn(ssn)
                .orElseThrow(() -> new IllegalArgumentException("Patient with SSN " + ssn + " not found"));
    }
}
