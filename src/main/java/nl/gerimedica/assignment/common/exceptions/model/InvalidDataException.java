package nl.gerimedica.assignment.common.exceptions.model;

public class InvalidDataException extends BaseHospitalException {

    public InvalidDataException(String message, Object... args) {
        super(message, args);
    }
}
