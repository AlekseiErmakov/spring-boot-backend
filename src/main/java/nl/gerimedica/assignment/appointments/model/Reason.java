package nl.gerimedica.assignment.appointments.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Reason {
    CHECKUP("Checkup"),
    FOLLOW_UP("Follow-up"),
    X_RAY("X-Ray");

    private static final Map<String, Reason> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<>();
        for (Reason r : Reason.values()) {
            VALUE_MAP.put(r.value.toLowerCase(), r);
        }
    }

    private final String value;

    @JsonCreator
    public static Reason fromValue(String value) {
        Reason reason = VALUE_MAP.get(value.toLowerCase());
        if (reason != null) {
            return reason;
        }
        throw new IllegalArgumentException("Unknown reason: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
