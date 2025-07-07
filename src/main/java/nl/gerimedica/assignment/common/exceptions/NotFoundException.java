package nl.gerimedica.assignment.common.exceptions;

public class NotFoundException extends BaseHospitalException {

    public NotFoundException(String message, Object... args) {
        super(message, args);
    }
}
