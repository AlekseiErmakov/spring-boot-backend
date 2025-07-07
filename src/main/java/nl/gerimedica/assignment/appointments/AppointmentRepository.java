package nl.gerimedica.assignment.appointments;

import java.util.List;
import java.util.Optional;
import nl.gerimedica.assignment.appointments.model.Appointment;
import nl.gerimedica.assignment.appointments.model.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findFirstByPatient_ssnOrderByDateDesc(String ssn);

    List<Appointment> findAllByReason(Reason reason);

    long deleteAllByPatient_ssn(String ssn);
}
