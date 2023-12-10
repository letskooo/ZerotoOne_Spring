package pm2_5.studypartner.dto.Multiple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MultiplesFindDTO {

    private Long multipleId;
    private String multipleTitle;
    private LocalDate multipleCreated;
}
