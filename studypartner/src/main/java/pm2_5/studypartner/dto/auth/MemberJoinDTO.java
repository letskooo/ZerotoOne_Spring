package pm2_5.studypartner.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberJoinDTO {

    @NotBlank(message = "회원 아이디는 공백일 수 없습니다")
    private String username;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다")
    private String password;
    @NotBlank(message = "이름은 공백일 수 없습니다")
    private String name;
    @Email
    @NotBlank
    private String email;
    private int age;
    private String sex;

}
