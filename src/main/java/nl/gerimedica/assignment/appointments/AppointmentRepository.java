package nl.gerimedica.assignment.appointments;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient_snn(String ssn);

    Optional<Appointment> findFirstByPatient_ssnOrderByDateDateDesc(String ssn);

    List<Appointment> findAllByReason(Reason reason);

    long deleteAllByPatient_snn(String ssn);
}
