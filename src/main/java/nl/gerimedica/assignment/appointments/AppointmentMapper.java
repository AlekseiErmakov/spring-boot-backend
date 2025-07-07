package nl.gerimedica.assignment.appointments;

import java.util.Collection;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppointmentMapper {

    public AppointmentDto toDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return new AppointmentDto(
                appointment.getId(),
                appointment.getReason(),
                appointment.getDate()
        );
    }

    public List<AppointmentDto> toDto(Collection<Appointment> appointments) {
        if (appointments == null) {
            return List.of();
        }
        return appointments.stream()
                .map(AppointmentMapper::toDto)
                .toList();
    }
}
