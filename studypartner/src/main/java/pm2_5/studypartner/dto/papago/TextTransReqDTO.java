package pm2_5.studypartner.dto.papago;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TextTransReqDTO {
        private String documentTitle;
        private Long memberId;
        private String source;
        private String target;
        private String text;

        public TextTransReqDTO(Long memberId, String source, String target, String text) {
                this.memberId = memberId;
                this.source = source;
                this.target = target;
                this.text = text;
        }
}
