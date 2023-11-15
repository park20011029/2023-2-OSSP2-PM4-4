package project.manager.server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.manager.server.dto.ResponseDto;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<?> handleApiException(ApiException e) {
        return ResponseDto.toResponseEntity(e);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleInvalidArgumentException(MethodArgumentNotValidException e) {
        return ResponseDto.toResponseEntity(e);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseDto.toResponseEntity(e);
    }
}
