package nl.gerimedica.assignment.hospital;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.appointments.AppointmentService;
import nl.gerimedica.assignment.appointments.dto.AppointmentDto;
import nl.gerimedica.assignment.appointments.dto.BulkAppointmentRequest;
import nl.gerimedica.assignment.common.exceptions.model.InvalidDataException;
import nl.gerimedica.assignment.patients.PatientService;
import nl.gerimedica.assignment.patients.model.Patient;
import nl.gerimedica.assignment.utils.HospitalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalService {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Transactional
    public List<AppointmentDto> bulkCreateAppointments(BulkAppointmentRequest bulkAppointmentRequest) {
        Patient patient = patientService.getPatient(bulkAppointmentRequest.patientName(), bulkAppointmentRequest.patientSsn());
        List<AppointmentDto> appointmentDtos = appointmentService.saveBulkAppointments(
                bulkAppointmentRequest.appointments(),
                patient
        );
        HospitalUtils.recordUsage("Bulk appointments created for patient: " + patient.getSsn());
        return appointmentDtos;
    }

    @Transactional
    public void deleteAppointmentsBySSN(String ssn) {
        if (patientService.existsBySsn(ssn)) {
            log.info("Deleting appointments for SSN: {}", ssn);
            appointmentService.deleteAppointmentsBySsn(ssn);
            HospitalUtils.recordUsage("Bulk appointments created for patient: " + ssn);
        } else {
            throw new InvalidDataException("Invalid SSN: %s", ssn);
        }
    }

}
