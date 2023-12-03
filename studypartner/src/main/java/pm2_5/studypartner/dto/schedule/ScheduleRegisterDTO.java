package pm2_5.studypartner.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleRegisterDTO {

    @NotNull(message = "회원 아이디를 꼭 입력해주세요!")
    private Long memberId;

    @NotBlank(message = "제목을 반드시 입력해주세요!")
    private String scheduleTitle;

    private String scheduleContent;

    @NotNull(message = "날짜를 꼭 입력해주세요!")
    private LocalDate scheduleDate;
}
