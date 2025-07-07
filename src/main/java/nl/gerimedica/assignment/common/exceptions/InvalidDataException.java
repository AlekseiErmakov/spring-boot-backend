package nl.gerimedica.assignment.common.exceptions;

public class InvalidDataException extends BaseHospitalException {

    public InvalidDataException(String message, Object... args) {
        super(message, args);
    }
}
