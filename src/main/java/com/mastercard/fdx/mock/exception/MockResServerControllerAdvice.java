package com.mastercard.fdx.mock.exception;

import com.mastercard.fdx.mock.utilities.CommonUtilities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MockResServerControllerAdvice {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleUnknownExceptions(HttpServletRequest request, final Exception ex) {
        logGenericException(ex);
        return getError(HttpStatus.INTERNAL_SERVER_ERROR, "We have encountered some internal error in processing your request.", ex.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    public final ResponseEntity<ErrorResponse> handleSecurityExceptions(HttpServletRequest request, final SecurityException ex) {
        logGenericException(ex);
        return getError(HttpStatus.UNAUTHORIZED, "We have encountered some internal error in processing your request.", ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingHeader(MissingRequestHeaderException ex) {
        logGenericException(ex);
        return getError(HttpStatus.BAD_REQUEST, "Missing Required Header", ex.getHeaderName());
    }

    private void logGenericException(Exception ex) {
        log.error("Error name: {}, Error Message: {}, Stack trace: {}!",
                ex.getClass().getSimpleName(), ex.getMessage(),
                CommonUtilities.getStringOfStackTrace(ex.getStackTrace()));
    }

    private ResponseEntity<ErrorResponse> getError(HttpStatus status, String errMsg, String errDesc) {
        ErrorResponse error = new ErrorResponse(status.value(), errMsg, errDesc);
        return new ResponseEntity<>(error, status);
    }

}
