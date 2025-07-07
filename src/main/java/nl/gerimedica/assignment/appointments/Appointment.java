package nl.gerimedica.assignment.appointments;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import nl.gerimedica.assignment.patients.Patient;

@Entity
@Getter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Reason reason;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {
    }

    public Appointment(Reason reason, LocalDate date, Patient patient) {
        this.reason = reason;
        this.date = date;
        this.patient = patient;
    }

}
