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
        Claims claims = getClaimsBodyByToken(token);

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

    private Claims getClaimsBodyByToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(accessSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(String secretKey) {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }


//    public boolean isValidToken(String token) {
//        // parseClaimsJws 메서드를 호출하면 기본적인 포맷을 검증하고, jwt 를 생성할 때 사용했던 secretKey 로 서명했을 때
//        // 토큰에 포함된 signature 와 동일한 signature 가 생성되는지 확인합니다.
//        // Header.Payload 에 대해서 동일한 secretKey 로 서명했을 때 생성된 signature 는 항상 같아야 합니다.
//        // 만약 다르다면 Header.Payload의 값이 변조되었다고 판단할 수 있겠죠.
//        // https://targetcoders.com/jjwt-%EC%82%AC%EC%9A%A9-%EB%B0%A9%EB%B2%95/
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(getSigningKey(accessSecretKey))
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (SecurityException e) {
//            log.info("잘못된 JWT 서명입니다. SecurityException={}", token); // io.jsonwebtoken.security.SecurityException 여기 Exception 을 잘 모르겠다.
//        } catch (MalformedJwtException e) {
//            log.info("잘못된 JWT 서명입니다. MalformedJwtException={}", token);
//        } catch (ExpiredJwtException e) {
//            log.info("만료된 토큰입니다. ExpiredJwtException={}", token);
//        } catch (UnsupportedJwtException e) {
//            log.info("지원하지 않는 토큰입니다. UnsupportedJwtException={}", token);
//        } catch (IllegalArgumentException e) {
//            log.info("토큰이 잘못되었습니다. IllegalArgumentException={}", token);
//        }
//        return false;
//    }

}
