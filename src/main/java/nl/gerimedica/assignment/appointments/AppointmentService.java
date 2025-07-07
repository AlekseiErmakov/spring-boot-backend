package nl.gerimedica.assignment.appointments;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.appointments.dto.AppointmentDto;
import nl.gerimedica.assignment.appointments.dto.AppointmentRequest;
import nl.gerimedica.assignment.appointments.model.Appointment;
import nl.gerimedica.assignment.appointments.model.Reason;
import nl.gerimedica.assignment.common.exceptions.model.NotFoundException;
import nl.gerimedica.assignment.patients.model.Patient;
import nl.gerimedica.assignment.utils.HospitalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Transactional
    public List<AppointmentDto> saveBulkAppointments(List<AppointmentRequest> appointments, Patient patient) {
        return AppointmentMapper.toDto(appointmentRepository.saveAll(
                appointments.stream()
                        .map(appointmentRequest -> new Appointment(
                                appointmentRequest.reason(),
                                appointmentRequest.date(),
                                patient
                        ))
                        .toList())
        );
    }

    @Transactional(readOnly = true)
    public AppointmentDto getLatestAppointmentBySsn(String ssn) {
        AppointmentDto appointmentDto = appointmentRepository.findFirstByPatient_ssnOrderByDateDesc(ssn)
                .map(AppointmentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("No appointments found for SSN: %s", ssn));
        HospitalUtils.recordUsage("Got latest appointment for patient: " + ssn);
        return appointmentDto;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentsByReason(Reason reason) {
        List<AppointmentDto> appointments = AppointmentMapper.toDto(appointmentRepository.findAllByReason(reason));
        HospitalUtils.recordUsage("Got latest appointment for reason: " + reason.getValue());
        return appointments;
    }

    @Transactional
    public void deleteAppointmentsBySsn(String ssn) {
        long amount = appointmentRepository.deleteAllByPatient_ssn(ssn);
        HospitalUtils.recordUsage("Deleted " + amount + "appointments for reason: " + ssn);
    }
}
