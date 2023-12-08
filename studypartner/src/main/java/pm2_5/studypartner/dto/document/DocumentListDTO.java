package pm2_5.studypartner.dto.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentListDTO {

    private String documentTitle;
    private LocalDate documentCreated;
}
