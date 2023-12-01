package pm2_5.studypartner.dto.document;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocImgTransReqDTO {
    String source;
    String target;
    MultipartFile image;
}
