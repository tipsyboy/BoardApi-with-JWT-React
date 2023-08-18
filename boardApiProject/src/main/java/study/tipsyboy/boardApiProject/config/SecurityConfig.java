package study.tipsyboy.boardApiProject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import study.tipsyboy.boardApiProject.security.jwt.config.JwtConfig;
import study.tipsyboy.boardApiProject.security.jwt.exception.JwtAccessDeniedHandler;
import study.tipsyboy.boardApiProject.security.jwt.exception.JwtAuthenticationEntryPoint;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtConfig jwtConfig;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // csrf 설정을 disable 한다.

                // Jwt 를 사용해서 인증처리를 하기 때문에
                // 스프링 시큐리티의 기본 방식인 Session 방식을 사용하지 않기 위해서 Stateless 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // H2-Console 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                
                // 요청에 대한 인증/인가 설정들
                .and()
                .authorizeRequests()
                .mvcMatchers("/api/auth/**").permitAll() // 인증 Api 요청은 인증 없어도 되야함.
                .anyRequest().authenticated()

                // Exception 관련 클래스들 등록
                .and()
                .exceptionHandling() 
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .apply(jwtConfig); // jwt 관련 설정 적용

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
