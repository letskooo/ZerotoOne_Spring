package pm2_5.studypartner.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pm2_5.studypartner.error.ApiException;
import pm2_5.studypartner.error.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class CustomRestAdvice {

    @ExceptionHandler(ApiException.class)
    private ResponseEntity<ErrorResponse> handleSignUpError(ApiException e, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .path(request.getServletPath())
                        .error(e.getStatus().getError())
                        .message(e.getMessage())
                        .build());
    }
}
