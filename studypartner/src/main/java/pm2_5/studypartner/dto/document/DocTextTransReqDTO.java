package pm2_5.studypartner.dto.document;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocTextTransReqDTO {
        private Long memberId;
        private String source;
        private String target;
        private String text;
}
