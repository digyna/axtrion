package org.digyna.axtrion.system.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends APIException {
    public BadRequestException(String message, final Object errors) {
        super(message, errors);
    }
    public BadRequestException(String message) {
        super(message, null);
    }

    public BadRequestException(BindingResult br) {
        super(buildMessage(br), buildErrors(br));
    }

    private static List<String> buildErrors(BindingResult br) {
        return br.getFieldErrors()
                .stream()
                .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
                .toList();
    }

    private static String buildMessage(BindingResult br) {
        return "Validation failed for object='" + br.getObjectName() + "'. Error count: " + br.getErrorCount();
    }
}
