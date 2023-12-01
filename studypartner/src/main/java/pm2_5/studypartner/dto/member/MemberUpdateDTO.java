package pm2_5.studypartner.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberUpdateDTO {

    @NotBlank(message = "값을 입력해주세요!")
    private String name;

    @Email(message = "이메일 형식을 지켜주세요!")
    @NotBlank(message = "값을 입력해주세요!")
    private String email;
}
