package com.arnaud.p3.ChaTop.exception;

import com.arnaud.p3.ChaTop.controller.AuthController;
import com.arnaud.p3.ChaTop.controller.MessageController;
import com.arnaud.p3.ChaTop.controller.RentalsController;
import com.arnaud.p3.ChaTop.controller.UsersController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackages = "com.arnaud.p3.ChaTop.controller")
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex,
                                                            HttpServletRequest req) {
    int statusCode = ex.getStatusCode().value();

    HttpStatus resolved = HttpStatus.resolve(statusCode);
    String error = resolved.getReasonPhrase();

    ErrorResponse body = new ErrorResponse(
      LocalDateTime.now(),
      statusCode,
      error,
      ex.getReason(),
      req.getRequestURI()
    );

    return ResponseEntity
      .status(statusCode)
      .body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllOthers(Exception ex,
                                                       HttpServletRequest req) {
    ErrorResponse body = new ErrorResponse(
      LocalDateTime.now(),
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
      ex.getMessage(),
      req.getRequestURI()
    );
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(body);
  }
}
