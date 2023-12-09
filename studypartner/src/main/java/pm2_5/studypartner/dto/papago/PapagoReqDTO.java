package pm2_5.studypartner.dto.papago;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PapagoReqDTO {
    private String source;
    private String target;
    private String text;
}
