package nl.gerimedica.assignment.patients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.common.exceptions.model.InvalidDataException;
import nl.gerimedica.assignment.patients.dto.PatientCreateRequest;
import nl.gerimedica.assignment.patients.model.Patient;
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
    }

    @Transactional(readOnly = true)
    public boolean existsBySsn(String ssn) {
        return patientRepository.existsBySsn(ssn);
    }

    @Transactional
    public Long createPatient(PatientCreateRequest patientCreateRequest) {
        if (patientRepository.existsBySsn(patientCreateRequest.ssn())) {
            log.warn("Patient with SSN {} already exists", patientCreateRequest.ssn());
            throw new InvalidDataException("Patient with SSN %s already exists", patientCreateRequest.ssn());
        }
        return patientRepository.save(new Patient(patientCreateRequest.name(), patientCreateRequest.ssn())).getId();
    }
}
