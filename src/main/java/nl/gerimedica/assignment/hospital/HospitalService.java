package nl.gerimedica.assignment.hospital;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.appointments.Appointment;
import nl.gerimedica.assignment.appointments.AppointmentRepository;
import nl.gerimedica.assignment.appointments.BulkAppointmentRequest;
import nl.gerimedica.assignment.appointments.Reason;
import nl.gerimedica.assignment.patients.Patient;
import nl.gerimedica.assignment.patients.PatientRepository;
import nl.gerimedica.assignment.patients.PatientService;
import nl.gerimedica.assignment.utils.HospitalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalService {
    private final PatientService patientService;
    @Autowired
    PatientRepository patientRepo;
    @Autowired
    AppointmentRepository appointmentRepo;

    public List<Appointment> bulkCreateAppointments(
            String patientName,
            String ssn,
            BulkAppointmentRequest bulkAppointmentRequest
    ) {
        Patient found = patientService.getPatient(patientName, ssn);

        List<Appointment> createdAppointments = new ArrayList<>();
        List<Reason> reasons = bulkAppointmentRequest.reasons();
        List<LocalDate> dates = bulkAppointmentRequest.dates();
        int loopSize = Math.min(reasons.size(), dates.size());
        for (int i = 0; i < loopSize; i++) {
            Reason reason = reasons.get(i);
            LocalDate date = dates.get(i);
            Appointment appt = new Appointment(reason, date, found);
            createdAppointments.add(appt);
        }

        for (Appointment appt : createdAppointments) {
            appointmentRepo.save(appt);
        }

        for (Appointment appt : createdAppointments) {
            log.info("Created appointment for reason: {} [Date: {}] [Patient SSN: {}]", appt.getReason(), appt.getDate(), appt.getPatient().getSsn() );
        }

        HospitalUtils.recordUsage("Bulk create appointments");

        return createdAppointments;
    }


    public List<Appointment> getAppointmentsByReason(String reasonKeyword) {
        List<Appointment> allAppointments = appointmentRepo.findAll();
        List<Appointment> matched = new ArrayList<>();

        for (Appointment ap : allAppointments) {
            if (ap.getReason().getValue().contains(reasonKeyword)) {
                matched.add(ap);
            }
        }

        List<Appointment> finalList = new ArrayList<>();
        for (Appointment ap : matched) {
            if (ap.getReason().getValue().equalsIgnoreCase(reasonKeyword)) {
                finalList.add(ap);
            }
        }

        HospitalUtils utils = new HospitalUtils();
        utils.recordUsage("Get appointments by reason");

        return finalList;
    }

    public void deleteAppointmentsBySSN(String ssn) {
        Patient patient = patientService.findPatientBySsn(ssn);
        if (patient == null) {
            return;
        }
        List<Appointment> appointments = patient.getAppointments();
        appointmentRepo.deleteAll(appointments);
    }

    public Appointment findLatestAppointmentBySSN(String ssn) {
        Patient patient = patientService.findPatientBySsn(ssn);
        if (patient == null || patient.getAppointments() == null || patient.getAppointments().isEmpty()) {
            return null;
        }

        Appointment latest = null;
        for (Appointment appt : patient.getAppointments()) {
            if (latest == null) {
                latest = appt;
            } else {
                if (appt.getDate().compareTo(latest.getDate()) > 0) {
                    latest = appt;
                }
            }
        }

        return latest;
    }
}
