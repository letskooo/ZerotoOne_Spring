package org.zerock.zerotoone.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.zerotoone.Util.JWTUtil;
import org.zerock.zerotoone.security.MemberDetailsService;
import org.zerock.zerotoone.security.filter.MemberLoginFilter;
import org.zerock.zerotoone.security.handler.MemberLoginSuccessHandler;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {

    private final MemberDetailsService memberDetailsService;

    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        // AuthenticationManager 설정
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder());

        // AuthenticationManger 생성
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        // authenticationManager로 설정
        http.authenticationManager(authenticationManager);

        // MemberLoginFilter
        // 로그인 처리 경로 설정. 사용자가 이 경로로 로그인 요청을 보낼 때 실행
        MemberLoginFilter memberLoginFilter = new MemberLoginFilter("/login");
        memberLoginFilter.setAuthenticationManager(authenticationManager);

        // MemberLoginSuccessHandler
        MemberLoginSuccessHandler memberLoginSuccessHandler = new MemberLoginSuccessHandler(jwtUtil);
        memberLoginFilter.setAuthenticationSuccessHandler(memberLoginSuccessHandler);

        // MemberLoginFilter 위치 설정
        http.addFilterBefore(memberLoginFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("---------configure-----------");

        // CSRF 토큰 비활성화
        http.csrf().disable();
        // 세션 생성 안하도록 설정.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();

    }
}
