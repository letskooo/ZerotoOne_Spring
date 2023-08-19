package org.zerock.zerotoone.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.zerotoone.Util.JWTUtil;
import org.zerock.zerotoone.security.exception.AccessTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.server.ServerCloneException;
import java.util.Map;

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

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {

        String headStr = request.getHeader("Authorization");

        if (headStr == null || headStr.length() < 8){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        String tokenType = headStr.substring(0, 6);
        String tokenStr = headStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch (MalformedJwtException malformedJwtException){
            log.error("-------MalformedJwtException----------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);

        } catch(SignatureException signatureException) {
            log.error("-------SignatureException-------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);

        } catch(ExpiredJwtException expiredJwtException){
            log.error("---------ExpiredJwtException-----------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);

        }

    }

}
