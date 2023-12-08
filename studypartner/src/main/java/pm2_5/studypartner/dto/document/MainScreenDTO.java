package pm2_5.studypartner.dto.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MainScreenDTO {

    private String memberName;
    private int count;
    private List<DocumentListDTO> documents;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DocumentListDTO {
        private Long documentId;
        private String documentTitle;
        private LocalDate documentCreated;
    }
}
