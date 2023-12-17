package project.manager.server.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import project.manager.server.dto.exception.ExceptionDto;
import project.manager.server.exception.ErrorDefine;

import com.google.gson.Gson;

@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorDefine errorCode = (ErrorDefine) request.getAttribute("exception");

        if (errorCode == null) {
            setErrorResponse(response, ErrorDefine.NOT_END_POINT);
        } else {
            switch (errorCode) {
                case USER_NOT_FOUND -> { setErrorResponse(response, ErrorDefine.USER_NOT_FOUND); }
                case ACCESS_DENIED -> { setErrorResponse(response, ErrorDefine.ACCESS_DENIED); }
                case TOKEN_MALFORMED -> { setErrorResponse(response, ErrorDefine.TOKEN_MALFORMED); }
                case TOKEN_EXPIRED -> { setErrorResponse(response, ErrorDefine.TOKEN_EXPIRED); }
                case TOKEN_TYPE -> { setErrorResponse(response, ErrorDefine.TOKEN_TYPE); }
                case TOKEN_UNSUPPORTED -> { setErrorResponse(response, ErrorDefine.TOKEN_UNSUPPORTED); }
                case TOKEN_UNKNOWN -> { setErrorResponse(response, ErrorDefine.TOKEN_UNKNOWN); }
            }
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorDefine errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> map = new HashMap<>();
        map.put("success", Boolean.FALSE);
        map.put("data", null);
        map.put("error", new ExceptionDto(errorCode));

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(map);
        response.getWriter().print(jsonResponse);
    }
}
