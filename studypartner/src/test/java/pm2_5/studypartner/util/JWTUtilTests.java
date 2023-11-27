package pm2_5.studypartner.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
public class JWTUtilTests {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate() {

        Map<String, Object> claimMap = Map.of("username", "ABCDE");

        String jwtStr = jwtUtil.generateToken(claimMap, 1);

        log.info(jwtStr);
    }

    @Test
    public void testValidate(){

        // 유효 시간이 지난 토큰
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTg5NDMzODMsImlhdCI6MTY5ODk0MzMyMywidXNlcm5hbWUiOiJBQkNERSJ9.XzUQIX8_xKQdbfgizg3hcF8NCd_eBR8aYXR2SKIOheY";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info(claim.toString());

    }

    @Test
    public void testAll(){

        String jwtStr = jwtUtil.generateToken(Map.of("username", "AAAA", "email", "aaaa@bbb.com"), 1);

        log.info(jwtStr);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info("username: " + claim.get("username"));
        log.info("email: " + claim.get("email"));

    }
}
