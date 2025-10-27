package org.digyna.axtrion.system.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends APIException{
    public ConflictException(String message) {
        super(message, null);
    }
}
