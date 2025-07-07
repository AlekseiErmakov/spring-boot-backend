package nl.gerimedica.assignment.common.exceptions.model;

public class NotFoundException extends BaseHospitalException {

    public NotFoundException(String message, Object... args) {
        super(message, args);
    }
}
