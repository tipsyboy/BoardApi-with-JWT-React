package study.tipsyboy.boardApiProject.security.jwt.config;

import io.jsonwebtoken.JwtHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import study.tipsyboy.boardApiProject.security.jwt.filter.JwtCustomFilter;
import study.tipsyboy.boardApiProject.security.jwt.util.TokenProvider;

@RequiredArgsConstructor
@Component
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtCustomFilter customFilter = new JwtCustomFilter(tokenProvider);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
