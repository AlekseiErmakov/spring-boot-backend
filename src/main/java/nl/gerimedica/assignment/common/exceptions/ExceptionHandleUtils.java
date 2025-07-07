package nl.gerimedica.assignment.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionHandleUtils {

    public String getRequestInfoMessage(HttpServletRequest request) {
        return String.format("During the execution of %s request to %s path. ", request.getMethod(),
                request.getRequestURI());
    }
}
