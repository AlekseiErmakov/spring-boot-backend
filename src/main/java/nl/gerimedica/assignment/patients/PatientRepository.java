package nl.gerimedica.assignment.patients;

import java.util.Optional;
import nl.gerimedica.assignment.patients.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findBySsn(String ssn);

    boolean existsBySsn(String ssn);
}
