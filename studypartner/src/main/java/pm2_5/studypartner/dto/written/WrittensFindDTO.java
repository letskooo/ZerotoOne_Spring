package pm2_5.studypartner.dto.written;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WrittensFindDTO {

    private Long writtenId;
    private String writtenTitle;
    private LocalDate writtenCreated;
}
