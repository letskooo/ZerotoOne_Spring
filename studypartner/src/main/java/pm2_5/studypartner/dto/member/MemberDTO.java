package pm2_5.studypartner.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {    // 회원 조회 응답 DTO

    // 회원 로그인 아이디
    private String username;

    // 회원 이름
    private String name;

    // 회원 이메일
    private String email;
}
