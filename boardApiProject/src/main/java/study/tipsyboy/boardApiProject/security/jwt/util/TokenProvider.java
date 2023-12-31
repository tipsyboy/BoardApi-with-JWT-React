package study.tipsyboy.boardApiProject.security.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private final String accessSecretKey;
    private final String refreshSecretKey;

    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; // 30분
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    public TokenProvider(@Value("${jwt.secretKey}") String accessSecretKey,
                         @Value("${jwt.refreshKey}") String refreshSecretKey) {
        this.accessSecretKey = accessSecretKey;
        this.refreshSecretKey = refreshSecretKey;
    }

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, ACCESS_TOKEN_EXPIRE_TIME, accessSecretKey);
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, REFRESH_TOKEN_EXPIRE_TIME, refreshSecretKey);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsBodyByToken(token, accessSecretKey); // 토큰과 시크릿 키를 사용해서 토큰 해석

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities
                = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims parseRefreshToken(String token) {
        return getClaimsBodyByToken(token, refreshSecretKey);
    }

    // JWT 유효성을 검증한다.
    public boolean isValidToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey(accessSecretKey))
                .build()
                .parseClaimsJws(token); // 만약 유효한 토큰이 아닌 경우에 여기서 에러가 터짐
        // -> 그렇게되면, isValidToken()을 호출한 위치에서 리턴 값을 받는 대신에 예외를 받으니, 예외처리 해주면됨.

        return true; // 파싱이 이루어진 경우에는 문제가 없는 경우
    }

    // ===== ===== //
    private String createToken(Authentication authentication,
                               Long expire,
                               String secretKey) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(expiredTime)
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    private Claims getClaimsBodyByToken(String token, String secretKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Key getSigningKey(String secretKey) {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

}
