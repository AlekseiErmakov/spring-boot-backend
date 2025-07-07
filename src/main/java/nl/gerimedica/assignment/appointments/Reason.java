package nl.gerimedica.assignment.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Reason {
    CHECKUP("Checkup"),
    FOLLOW_UP("Follow-up"),
    X_RAY("X-Ray");

    private final String value;
}
