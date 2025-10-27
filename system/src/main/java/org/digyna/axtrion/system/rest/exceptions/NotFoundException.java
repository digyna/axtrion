package org.digyna.axtrion.system.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends APIException {
    public NotFoundException(String message) {
        super(message, null);
    }
}
