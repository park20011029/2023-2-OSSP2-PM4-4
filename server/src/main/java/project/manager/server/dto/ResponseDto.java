package project.manager.server.dto;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import project.manager.server.exception.ApiException;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean isSuccess;
    private final T responseDto;
    private ExceptionDto error;

    public ResponseDto(@Nullable T responseDto) {
        this.isSuccess = true;
        this.responseDto = responseDto;
    }

    public static ResponseEntity<?> toResponseEntity(ApiException e) {
        return ResponseEntity.status(e.getError().getHttpStatus())
                .body(
                        ResponseDto.builder()
                                .isSuccess(false)
                                .responseDto(null)
                                .error(new ExceptionDto(e.getError()))
                                .build());
    }

    public static ResponseEntity<Object> toResponseEntity(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseDto.builder()
                                .isSuccess(false)
                                .responseDto(null)
                                .error(new InvalidateArgumentExceptionDto(e))
                                .build());
    }

    public static ResponseEntity<Object> toResponseEntity(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseDto.builder()
                                .isSuccess(false)
                                .responseDto(null)
                                .error(new ExceptionDto(e))
                                .build());
    }
}