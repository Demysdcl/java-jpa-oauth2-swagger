package com.hugosilva.curso.ws.resources.exception;

import com.hugosilva.curso.ws.services.exception.ObjectAlreadyExistException;
import com.hugosilva.curso.ws.services.exception.ObjectNotEnabledException;
import com.hugosilva.curso.ws.services.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {
    //error 404
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    //error 409
    @ExceptionHandler({ ObjectAlreadyExistException.class })
    public ResponseEntity<Object> handleObjectAlreadyExist(final
                                                           RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(), "UserAlreadyExist", e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    //error 401
    @ExceptionHandler(value = {ObjectNotEnabledException.class})
    public ResponseEntity<Object> handleObjectNotEnabled(final
                                                         RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(System.currentTimeMillis(),
                status.value(), "UserNotEnable", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
