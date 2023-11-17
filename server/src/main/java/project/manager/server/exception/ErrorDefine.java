package project.manager.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorDefine {
    // Bad Request
    INVALID_ARGUMENT("4000", HttpStatus.BAD_REQUEST, "Bad Request: Invalid Arguments"),

    // UNAUTHORIZED: 401
    ACCESS_DENIED("4010", HttpStatus.UNAUTHORIZED, "Unauthorized: Access denied"),
    TOKEN_INVALID("4011", HttpStatus.UNAUTHORIZED, "Unauthorized: Invalid Token"),
    TOKEN_MALFORMED("4012", HttpStatus.UNAUTHORIZED, "Unauthorized: Token malformed"),
    TOKEN_EXPIRED("4013", HttpStatus.UNAUTHORIZED, "Unauthorized: Token expired"),
    TOKEN_TYPE("4014", HttpStatus.UNAUTHORIZED, "Unauthorized: Wrong token type"),
    TOKEN_UNSUPPORTED("4015", HttpStatus.UNAUTHORIZED, "Unauthorized: Unsupported token"),
    TOKEN_UNKNOWN("4016", HttpStatus.UNAUTHORIZED, "Unknown Error"),

    // NOT_FOUND: 404
    USER_NOT_FOUND("4040", HttpStatus.NOT_FOUND, "Not Found: User Not Found"),
    USER_WITHDRAWAL("4041", HttpStatus.NOT_FOUND, "Not Found: User withdraw"),
    RESUME_NOT_FOUND("4042", HttpStatus.NOT_FOUND, "NOT Found: Resume Not Found"),
    ENTITY_NOT_FOUND("4043", HttpStatus.NOT_FOUND, "NOT Found: Entity Not Found"),
    BOARD_NOT_FOUND("4044", HttpStatus.NOT_FOUND, "NOT Found: Board Not Found" ),

    // CONFLICT: 409
    EMAIL_EXIST(
            "4090", HttpStatus.CONFLICT, "Conflict: An account with this email already exists."),
    USER_EXIST("4091", HttpStatus.CONFLICT, "Conflict: An account with this user already exists."),
    NICKNAME_EXIST(
            "4092", HttpStatus.CONFLICT, "Conflict: An account with this nickname already exists."),
    RESUME_EXIST(
            "4093", HttpStatus.CONFLICT, "Conflict: An account with this Resume already exits"),
    Building_EXIST("4094", HttpStatus.CONFLICT, "Conflict: An account with this building post of a gongmopost"),
    // GONE: 410
    USER_DELETE("4010", HttpStatus.GONE, "GONE: User delete data"),
    USER_EXPEL("4011", HttpStatus.GONE, "GONE: USER expelled"),

    // INTERNAL_SERER_ERROR: 500
    SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "Server Error: Internal server error");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
