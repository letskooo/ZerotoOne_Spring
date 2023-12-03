package pm2_5.studypartner.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdateDTO {

    @NotBlank(message = "제목은 반드시 입력해주세요!")
    private String scheduleTitle;

    private String scheduleContent;

    @NotNull(message = "날짜를 꼭 입력해주세요!")
    private LocalDate scheduleDate;
}
