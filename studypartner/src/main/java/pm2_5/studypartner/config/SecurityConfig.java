package pm2_5.studypartner.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pm2_5.studypartner.security.CustomUserDetailsService;
import pm2_5.studypartner.security.filter.LoginFilter;
import pm2_5.studypartner.security.filter.RefreshTokenFilter;
import pm2_5.studypartner.security.filter.TokenCheckFilter;
import pm2_5.studypartner.security.handler.LoginSuccessHandler;
import pm2_5.studypartner.util.JWTUtil;

@Configuration
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 정적 자원 시큐리티 적용 안함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("---------web configure------------");

        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        // AuthenticationManager 설정
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                        .passwordEncoder(passwordEncoder());

        // AuthenticationManager 가져오기
        AuthenticationManager authenticationManager =
                authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);

        // LoginFilter
        LoginFilter loginFilter = new LoginFilter("/login");
        loginFilter.setAuthenticationManager(authenticationManager);

        // LoginSuccessHandler
        LoginSuccessHandler successHandler = new LoginSuccessHandler(jwtUtil);
        // SuccessHandler 세팅
        loginFilter.setAuthenticationSuccessHandler(successHandler);

        // LoginFilter 위치 지정
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);


        log.info("---------SecurityFilterChain Configure--------------");

        // api로 시작하는 모든 경로는 TokenCheckFilter 동작
        http.addFilterBefore(
                tokenCheckFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class
        );

        // refreshToken 호출 처리
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil), TokenCheckFilter.class);


        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil){
        return new TokenCheckFilter(jwtUtil);
    }

}