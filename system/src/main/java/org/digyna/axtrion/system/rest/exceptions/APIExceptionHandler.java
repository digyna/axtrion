package org.digyna.axtrion.system.rest.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class APIExceptionHandler extends AbstractErrorController {
    private static final String ERROR_PATH = "/error";
    private final ErrorAttributes errorAttributes;
    @Autowired
    public APIExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(path = ERROR_PATH)
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> errors = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        getApiException(request).ifPresent(apiError -> {
            errors.put("message" , apiError.getMessage());
            errors.put("success", apiError.getSuccess());

            if(apiError.getErrors() != null) {
                errors.put("errors", apiError.getErrors());
            }
        });
        // If you don't want to expose exception!
        errors.remove("exception");
        errors.put("timestamp", new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.status(status).body(errors);
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Optional<APIException> getApiException(HttpServletRequest request) {
        ServletWebRequest attributes = new ServletWebRequest(request);
        Throwable throwable = errorAttributes.getError(attributes);
        if (throwable instanceof APIException) {
            APIException exception = (APIException) throwable;
            return Optional.of(exception);
        }

        if (throwable instanceof HttpMessageNotReadableException) {
            APIException exception = new APIException();
            Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: \\[(.*?)\\]");
            String type = "";
            String part = " must be one of the following values: ";
            String values = "";
            String message = "";

            if (throwable.getCause() != null && throwable.getCause() instanceof InvalidFormatException) {
                Matcher match = ENUM_MSG.matcher(throwable.getCause().getMessage());
                if (match.find()) {
                    values = match.group(1).toString();
                }

                ENUM_MSG = Pattern.compile("\\[\\\"(.*?)\\\"\\]");
                match = ENUM_MSG.matcher(throwable.getCause().getMessage());
                if (match.find()) {
                    type = match.group(1).toString();
                }

                ENUM_MSG = Pattern.compile(": not a valid (.*)");
                match = ENUM_MSG.matcher(throwable.getCause().getMessage());
                if (match.find()) {
                    values = match.group(1).toString();
                    part = " not a valid ";
                }

                message = type.concat(part).concat(values);
                exception.setMessage(message);
            }

            if (throwable.getCause() != null && throwable.getCause() instanceof JsonParseException) {
                ENUM_MSG = Pattern.compile("was expecting \\(JSON (.*?)\\)");
                Matcher match = ENUM_MSG.matcher(throwable.getCause().getMessage());
                match = ENUM_MSG.matcher(throwable.getCause().getMessage());
                if (match.find()) {
                    message = "was expecting " + match.group(1).toString();
                }

                exception.setMessage(message);
            }

            return Optional.of(exception);
        }

        return Optional.empty();
    }
}
