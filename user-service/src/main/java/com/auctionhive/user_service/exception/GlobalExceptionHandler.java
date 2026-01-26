package com.auctionhive.user_service.exception;

import com.auctionhive.user_service.utils.BaseResponse;
import com.auctionhive.user_service.utils.ErrorCode;
import com.auctionhive.user_service.utils.Errors;
import com.auctionhive.user_service.utils.GsUtils;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.auctionhive.user_service.utils.GsUtils.error;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /* ===================== SECURITY ===================== */

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse> handleBadCredentials(
            BadCredentialsException ex) {

        log.warn("Bad credentials", ex);

        BaseResponse response = error(
                HttpStatus.UNAUTHORIZED,
                ErrorCode.INVALID_CREDENTIALS,
                Errors.ERROR_TYPE.SECURITY
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDenied(
            AccessDeniedException ex) {

        log.warn("Access denied", ex);

        BaseResponse response = error(
                HttpStatus.FORBIDDEN,
                ErrorCode.ACCESS_DENIED,
                Errors.ERROR_TYPE.SECURITY
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    /* ===================== VALIDATION ===================== */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .orElse("Validation failed");

        BaseResponse response = error(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR,
                Errors.ERROR_TYPE.USER
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(
            NoResourceFoundException ex
    ) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleConstraintViolation(
            ConstraintViolationException ex) {

        BaseResponse response = error(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR,
                Errors.ERROR_TYPE.USER
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /* ===================== GENERIC ===================== */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgument(
            IllegalArgumentException ex) {

        log.error("Illegal argument", ex);

        BaseResponse response = error(
                HttpStatus.BAD_REQUEST,
                ErrorCode.BAD_REQUEST,
                Errors.ERROR_TYPE.USER
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleGenericException(
            Exception ex) {

        log.error("Unhandled exception", ex);

        BaseResponse response = error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR,
                Errors.ERROR_TYPE.SYSTEM
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> handleBusinessException(
            BusinessException ex) {

        BaseResponse response = error(
                HttpStatus.BAD_REQUEST,
                ErrorCode.BUSINESS_ERROR,
                Errors.ERROR_TYPE.USER

        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
