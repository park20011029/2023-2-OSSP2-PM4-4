package project.manager.server.dto;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.manager.server.exception.ApiException;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private final Boolean isSuccess;
    private final T dtoObject;
    private ExceptionDto error;

    public ResponseDto(@Nullable T dtoObject) {
        this.isSuccess = true;
        this.dtoObject = dtoObject;
    }

    public static ResponseEntity<Object> toResponseEntity(ApiException e) {
        return ResponseEntity.status(e.getError().getHttpStatus())
                .body(
                        ResponseDto.builder()
                                .isSuccess(false)
                                .dtoObject(null)
                                .error(new ExceptionDto(e.getError()))
                                .build());
    }

    public static ResponseEntity<Object> toResponseEntity(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseDto.builder()
                                .isSuccess(false)
                                .dtoObject(null)
                                .error(new ExceptionDto(e))
                                .build());
    }
}
