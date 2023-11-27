package pm2_5.studypartner.error.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorStatus {

    MUSERNAME_EXIST("Duplicate Error", "아이디가 존재합니다!"),
    VALIDATION_ERROR("VALIDATION_ERROR", "적합하지 않은 입력!");

    private final String error;
    private final String message;

}
