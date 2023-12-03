package pm2_5.studypartner.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScheduleErrorStatus {       // 일정 엔티티 예외

    VALIDATION_ERROR("VALIDATION_ERROR", "적합하지 않은 입력!"),
    SCHEDULE_NOT_FOUND("Schedule Not Found Error", "일정이 존재하지 않습니다!");

    private final String error;
    private final String message;
}
