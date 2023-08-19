package org.zerock.zerotoone.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.zerotoone.Util.JWTUtil;
import org.zerock.zerotoone.security.exception.RefreshTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if(!path.equals(refreshPath)){
            log.info("skip refresh token filter........");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Refresh Token Filter...run......");

        Map<String, String> tokens = parseRequsetJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);

        try {
            checkAccessToken(accessToken);
        } catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return;
        }

        try {
            checkRefreshToken(refreshToken);
        } catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
        }
        Map<String, Object> refreshClaims = null;

        try{
            refreshClaims = checkRefreshToken(refreshToken);
            log.info(refreshClaims);
        } catch (RefreshTokenException refreshTokenException){
            refreshTokenException.sendResponseError(response);
            return;
        }

    }

    private void checkAccessToken(String accessToken) throws RefreshTokenException {

        try {
            jwtUtil.validateToken(accessToken);
        } catch (ExpiredJwtException expiredJwtException){
            log.info("Access Token has expired");
        } catch (Exception exception){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException {

        try{
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        } catch (ExpiredJwtException expiredJwtException){
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        } catch (MalformedJwtException malformedJwtException){
            log.error("------MalformedJwtException----------");
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        } catch (Exception exception){
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }


    private Map<String, String> parseRequsetJSON(HttpServletRequest request){

        try(Reader reader = new InputStreamReader(request.getInputStream())){

            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}