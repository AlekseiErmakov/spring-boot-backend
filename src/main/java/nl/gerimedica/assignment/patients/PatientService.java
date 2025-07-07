package nl.gerimedica.assignment.patients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.common.exceptions.model.InvalidDataException;
import nl.gerimedica.assignment.patients.dto.PatientCreateRequest;
import nl.gerimedica.assignment.patients.model.Patient;
import nl.gerimedica.assignment.utils.HospitalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public Patient getPatient(String patientName, String ssn) {
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

        Long patientId = patientRepository.save(new Patient(patientCreateRequest.name(), patientCreateRequest.ssn())).getId();
        HospitalUtils.recordUsage("Created patient with ID: " + patientId + " and SSN: " + patientCreateRequest.ssn());
        return patientId;
    }
}
