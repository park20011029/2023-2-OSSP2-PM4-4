package project.manager.server.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import project.manager.server.dto.exception.ExceptionDto;
import project.manager.server.exception.ErrorDefine;

import com.google.gson.Gson;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 필요한 권한이 없이 접근하려 할때 403
        setErrorResponse(response, ErrorDefine.ACCESS_DENIED);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorDefine errorDefine) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> map = new HashMap<>();
        map.put("success", Boolean.FALSE);
        map.put("data", null);
        map.put("error", new ExceptionDto(errorDefine));

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(map);
        response.getWriter().print(jsonResponse);
    }
}
