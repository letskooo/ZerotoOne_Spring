package pm2_5.studypartner.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import pm2_5.studypartner.security.exception.RefreshTokenException;
import pm2_5.studypartner.util.JWTUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (!path.equals(refreshPath)){
            log.info("----------skip refresh token filter-----------");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("------------Refresh Token Filter--------------");

        // 전송된 JSON 에서 Refresh Token 을 가져옴
        Map<String, String> token = parseRequestJSON(request);

        String refreshToken = token.get("refreshToken");

        log.info("refreshToken: " + refreshToken);

        Map<String, Object> refreshClaims = null;

        try {
            refreshClaims = checkRefreshToken(refreshToken);
            log.info(refreshClaims.toString());

            String memberId = (String) refreshClaims.get("memberId");

            // 새로운 Access Token 생성
            String accessTokenValue = jwtUtil.generateToken(Map.of("memberId", String.valueOf(memberId)), 1);

            log.info("accessToken: " + accessTokenValue);

            sendToken(accessTokenValue, response);

        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }
    }

    private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException {

        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        } catch (ExpiredJwtException expiredJwtException) {

            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        } catch (MalformedJwtException malformedJwtException) {

            log.error("----------MalformedJwtException-------------");
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        } catch (Exception exception){
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    private void sendToken(String accessTokenValue, HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue));

        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request){

        try (Reader reader = new InputStreamReader(request.getInputStream())){

            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
