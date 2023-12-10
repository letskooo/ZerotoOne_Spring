package pm2_5.studypartner.dto.written;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WrittenDTO {

    private Long documentId;

    private String title;

    private String question;

    private String answer;

}
