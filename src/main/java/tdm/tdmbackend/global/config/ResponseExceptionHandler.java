package tdm.tdmbackend.global.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tdm.tdmbackend.global.exception.BadRequestException;
import tdm.tdmbackend.global.exception.ExceptionResponse;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e){
        return ResponseEntity.badRequest().body(ExceptionResponse.fromBadRequest(e));
    }
}
