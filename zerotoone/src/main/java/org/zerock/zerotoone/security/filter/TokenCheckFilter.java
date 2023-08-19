package org.zerock.zerotoone.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.zerotoone.Util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.server.ServerCloneException;

@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 경로가 "/auth"로 시작하면 Token Check Filter를 거치도록

        if(!path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("----------Token Check Filter---------------");
        log.info("JWTUtil: " + jwtUtil);

        filterChain.doFilter(request, response);

    }
}
