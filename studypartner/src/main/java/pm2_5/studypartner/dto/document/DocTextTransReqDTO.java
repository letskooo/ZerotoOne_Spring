package pm2_5.studypartner.dto.document;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocTextTransReqDTO {
        private String documentTitle;
        private Long memberId;
        private String source;
        private String target;
        private String text;

        public DocTextTransReqDTO(Long memberId, String source, String target, String text) {
                this.memberId = memberId;
                this.source = source;
                this.target = target;
                this.text = text;
        }
}
