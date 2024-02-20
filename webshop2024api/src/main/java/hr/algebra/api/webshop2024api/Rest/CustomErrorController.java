package hr.algebra.api.webshop2024api.Rest;

import hr.algebra.api.webshop2024api.CustomResponseErrors.ErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            HttpStatus httpStatus = HttpStatus.resolve(statusCode);

            if (httpStatus != null) {
                switch (httpStatus) {
                    case NOT_FOUND:
                        return buildErrorResponse(HttpStatus.NOT_FOUND, "The requested resource was not found", request);
                    case UNAUTHORIZED:
                        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "You are not authorized!", request);
                    case FORBIDDEN:
                        return buildErrorResponse(HttpStatus.FORBIDDEN, "You are not allowed to access this resource!", request);
                    default:
                        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred", request);
                }
            }
        }

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred", request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, status);
    }
}

