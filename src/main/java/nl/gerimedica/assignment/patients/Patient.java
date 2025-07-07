package nl.gerimedica.assignment.patients;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import nl.gerimedica.assignment.appointments.Appointment;

@Entity
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ssn;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient patient)) return false;
        return Objects.equals(ssn, patient.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}
