package pm2_5.studypartner.dto.document;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocImgTransReqDTO {
    private String documentTitle;
    private Long memberId;
    private String source;
    private String target;
    private MultipartFile image;

}
