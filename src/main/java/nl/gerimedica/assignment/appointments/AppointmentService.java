package nl.gerimedica.assignment.appointments;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.common.exceptions.NotFoundException;
import nl.gerimedica.assignment.patients.Patient;
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
        return appointmentRepository.findFirstByPatient_ssnOrderByDateDateDesc(ssn)
                .map(AppointmentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("No appointments found for SSN: %s", ssn));
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentsByReason(Reason reason) {
        return AppointmentMapper.toDto(appointmentRepository.findAllByReason(reason));
    }

    @Transactional
    public void deleteAppointmentsBySsn(String ssn) {
        appointmentRepository.deleteAllByPatient_snn(ssn);
    }
}
