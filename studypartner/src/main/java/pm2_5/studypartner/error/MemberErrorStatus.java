package pm2_5.studypartner.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorStatus {     // 회원 엔티티 예외

    USERNAME_EXIST("Duplicate Error", "아이디가 존재합니다!"),
    VALIDATION_ERROR("VALIDATION_ERROR", "적합하지 않은 입력!"),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "회원이 존재하지 않습니다!");

    private final String error;
    private final String message;
}
