package hr.algebra.api.webshop2024api.ApiAdvice;

import hr.algebra.api.webshop2024api.CustomResponseErrors.ErrorResponse;
import hr.algebra.utils.CustomExceptions.CustomErrorResponse;
import hr.algebra.utils.CustomExceptions.CustomForbiddenException;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import hr.algebra.utils.CustomExceptions.CustomUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleCustomNotFoundException(CustomNotFoundException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getContextPath());
    }

    //VALIDATION ERRORS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        customErrorResponse.setMessage("Validation failed");
        customErrorResponse.setTimeStamp(System.currentTimeMillis());
        customErrorResponse.setErrors(errors);

        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    // General exception handler
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleGeneralException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof CustomUnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof CustomForbiddenException) {
            status = HttpStatus.FORBIDDEN;
        }

        return createErrorResponse(status, ex.getMessage(), request.getContextPath());
    }

    private ErrorResponse createErrorResponse(HttpStatus status, String message, String path) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(path);
        return errorResponse;
    }
}

