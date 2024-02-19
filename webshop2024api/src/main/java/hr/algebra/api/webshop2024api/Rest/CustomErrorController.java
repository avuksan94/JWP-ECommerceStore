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

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
                errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
                errorResponse.setMessage("The requested resource was not found");
                errorResponse.setPath(request.getRequestURI());

                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

