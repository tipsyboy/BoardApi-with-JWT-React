package study.tipsyboy.boardApiProject.security.jwt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import study.tipsyboy.boardApiProject.security.jwt.dto.JwtExceptionResponseDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String exception = (String) request.getAttribute("exception");
        if (exception != null) {
            for (JwtExceptionType exceptionType : JwtExceptionType.values()) {
                if (exceptionType.getCode().equals(exception)) {
                    log.error("entry point >> {}", exceptionType.getMessage());
                    setResponse(response, exceptionType, request.getRequestURI());
                }
            }
        }
    }

    private void setResponse(HttpServletResponse response,
                             JwtExceptionType exceptionCode,
                             String path) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JwtExceptionResponseDto jwtExceptionResponseDto = JwtExceptionResponseDto.builder()
                .path(path)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .exceptionCode(exceptionCode.getCode())
                .message(exceptionCode.getMessage())
                .build();

        // ObjectMapper를 사용하여 DTO 객체를 JSON 문자열로 변환 후 응답 본문에 기록
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(jwtExceptionResponseDto);
        response.getWriter().write(jsonResponse);

    }

}
