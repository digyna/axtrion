package org.digyna.axtrion.system.rest.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class APIException extends RuntimeException {
    public boolean success;
    public String message;
    public Object errors;

    APIException(String message, final Object errors) {
        this.success = false;
        this.message = message;
        this.errors = errors;
    }

    public boolean getSuccess() {
        return success;
    }
}
