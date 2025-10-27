package org.digyna.axtrion.system.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class UnavailableException extends APIException{
    public UnavailableException(String message) {
        super(message, null);
    }
}
