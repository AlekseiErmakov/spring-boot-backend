package nl.gerimedica.assignment.common.exceptions.model;

public abstract class BaseHospitalException extends RuntimeException {

    protected BaseHospitalException(String message, Object... args) {
        super(String.format(message, args));
    }
}
