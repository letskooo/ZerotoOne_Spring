package pm2_5.studypartner.dto.papago;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClovaReqDTO {
    private String version;
    private String requestId;
    private long timestamp;
    private String lang;
    private List<Image> images;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    // 이미지 정보를 담는 내부 클래스
    public static class Image {
        private String format;
        private String data;
        private String name;
    }
}
