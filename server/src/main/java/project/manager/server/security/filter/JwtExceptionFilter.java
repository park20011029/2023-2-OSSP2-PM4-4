package project.manager.server.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.filter.OncePerRequestFilter;

import project.manager.server.exception.ErrorDefine;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        boolean isException = false;
        try {
            filterChain.doFilter(request, response);
        }
        catch (SecurityException e) {
            log.error("FilterException throw SecurityException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.ACCESS_DENIED);
            isException = true;
        }
        catch (MalformedJwtException  e) {
            log.error("FilterException throw MalformedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.TOKEN_MALFORMED);
            isException = true;
        } catch (IllegalArgumentException e) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.TOKEN_TYPE);
            isException = true;
        } catch (ExpiredJwtException e) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.TOKEN_EXPIRED);
            isException = true;
        } catch (UnsupportedJwtException e) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.TOKEN_UNSUPPORTED);
            isException = true;
        } catch (JwtException e) {
            log.error("FilterException throw JwtException Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.TOKEN_UNKNOWN);
            isException = true;
        } catch (Exception e) {
            log.error("FilterException throw Exception Exception : {}", e.getMessage());
            request.setAttribute("exception", ErrorDefine.USER_NOT_FOUND);
            isException = true;
        }

        if (isException) {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(ConstantUrl.NO_NEED_AUTH_URLS).anyMatch(url -> url.equals(request.getServletPath()));
    }
}
