package project.manager.server.dto;

import project.manager.server.exception.ErrorDefine;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionDto {
    private final String code;
    private final String message;

    public ExceptionDto(ErrorDefine errorDefine) {
        this.code = errorDefine.getErrorCode();
        this.message = errorDefine.getMessage();
    }

    public ExceptionDto(Exception e) {
        this.code = Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value());
        this.message = e.getMessage();
    }
}