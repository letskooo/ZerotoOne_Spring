package pm2_5.studypartner.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.repository.MemberRepository;
import pm2_5.studypartner.util.JWTUtil;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("----------Login Success Handler------------");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 로그인 시 사용자의 정보(username)를 포함한 authentication 객체가 생성됨

        log.info(String.valueOf(authentication));
        log.info(authentication.getName());     // username

//        Map<String, Object> claim = Map.of("username", authentication.getName());

        Member member = memberRepository.findByUsername(authentication.getName()).get();


        Map<String, Object> claim = Map.of("memberId", String.valueOf(member.getId()));

        log.info(String.valueOf(member.getId()));

        // Access Token 유효 기간 1일
        String accessToken = jwtUtil.generateToken(claim, 1);

        // Refresh Token 유효 기간 30일
        String refreshToken = jwtUtil.generateToken(claim, 30);

        Gson gson = new Gson();

        Map<String, String> keyMap = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken);

        String jsonStr = gson.toJson(keyMap);

        response.getWriter().println(jsonStr);
    }
}