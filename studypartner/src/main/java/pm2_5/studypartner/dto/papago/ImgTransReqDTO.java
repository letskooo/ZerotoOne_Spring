package pm2_5.studypartner.dto.papago;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImgTransReqDTO {
    private String documentTitle;
    private Long memberId;
    private MultipartFile image;
}