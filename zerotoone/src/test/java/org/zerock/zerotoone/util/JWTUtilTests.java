package org.zerock.zerotoone.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.zerotoone.Util.JWTUtil;

import java.util.Map;

@SpringBootTest
@Log4j2
public class JWTUtilTests {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate(){

        Map<String, Object> claimMap = Map.of("username", "ABCDE");
        String jwtStr = jwtUtil.generateToken(claimMap, 1);
        log.info(jwtStr);

    }

    @Test
    public void testValidate(){

        // 유효 시간이 지난 토큰
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTI0MjU5NDIsImlhdCI6MTY5MjQyNTg4MiwidXNlcm5hbWUiOiJBQkNERSJ9.H_7hvxxh2R3rdnX4WPDIIU4LnmeQoWnY3w7GKGG9NGE";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info(claim);

    }

}
