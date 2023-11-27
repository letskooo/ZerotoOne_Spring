package pm2_5.studypartner.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

@Component
@Slf4j
public class JWTUtil {

    @Value("${pm2_5.studypartner.jwt.secret}")
    private String key;

    public String generateToken(Map<String, Object> valueMap, int days){

        log.info("generateKey..." + key);

        // Header
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // Payload
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

        int time = (60 * 24) * days;

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // Signature 처리
                .compact();

        return jwtStr;
    }

    public Map<String, Object> validateToken(String token) throws JwtException {

        Map<String, Object> claim = null;

        claim = Jwts.parser()
                .setSigningKey(key.getBytes())  // Set Key. 비밀키를 통해서 토큰을 까봄
                .parseClaimsJws(token)          // 파싱 및 검증, 실패 시 에러
                .getBody();

        return claim;
    }
}
