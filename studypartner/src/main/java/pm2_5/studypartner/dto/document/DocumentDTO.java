package pm2_5.studypartner.dto.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {

    // 문서 제목
    String documentTitle;

    // 한글 내용
    String koContent;

}
