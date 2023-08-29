package study.tipsyboy.boardApiProject.security.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import study.tipsyboy.boardApiProject.security.jwt.exception.JwtExceptionType;
import study.tipsyboy.boardApiProject.security.jwt.util.TokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /*
         * JwtCustomFilter 필터에선 전달받은 요청에서 [토큰이 존재하는지, 존재한다면 토큰이 유효한지]를 판단해서 SecurityContextHolder에 인증정보를 저장하는 역할을 한다.
         * 만약, 요청에 토큰이 존재하지 않거나, 유효하지 않아도 상관없다. 그냥 필터를 거쳐서 넘길뿐이다.
         * 왜냐하면 아직 이 요청이 토큰이 필요한 요청인지 모르기 때문이다.
         * 하지만, 필터를 모두 거친 후에 인증이 필요한 요청에서 인증정보가 없을 때의 처리를 어떻게 할 것인지에 대해서 생각해봐야 한다.
         *   -> 요청에 예외를 담아서 EntryPoint 로 보내는 방식을 사용함
         */

        String token = resolveToken(request);
        try {
            if (StringUtils.hasText(token) && tokenProvider.isValidToken(token)) {
                // 토큰이 있는 경우에 토큰을 통해서 인증 정보 객체인 Authentication 객체를 만든다.
                // 이때 getAuthentication()에서 토큰을 파싱하는 부분이 있는데, 토큰에 문제가 있는 경우 예외가 발생한다.
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (SecurityException e) {
            request.setAttribute("exception", JwtExceptionType.INVALID_TOKEN.getCode());
            log.error("잘못된 JWT 서명입니다. SecurityException={}", token); // io.jsonwebtoken.security.SecurityException 여기 Exception 을 잘 모르겠다.
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", JwtExceptionType.INVALID_TOKEN.getCode());
            log.error("잘못된 JWT 서명입니다. MalformedJwtException={}", token);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", JwtExceptionType.EXPIRED_TOKEN.getCode());
            log.error("이미 만료된 토큰입니다. ExpiredJwtException={}", token);
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", JwtExceptionType.UNSUPPORTED_TOKEN.getCode());
            log.error("지원하지 않는 토큰입니다. UnsupportedJwtException={}", token);
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", JwtExceptionType.INVALID_TOKEN.getCode());
            log.error("토큰이 잘못되었습니다. IllegalArgumentException={}", token);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String[] tokenArr = bearerToken.split(" ");
            return tokenArr[1];
        }
        return null;
    }
}
