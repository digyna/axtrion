package org.digyna.axtrion.system.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends APIException {
    public InternalServerException(String message, final Object errors) {
        super(message, errors);
    }
}
