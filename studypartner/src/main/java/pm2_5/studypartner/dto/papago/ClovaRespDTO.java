package pm2_5.studypartner.dto.papago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClovaRespDTO {
    private String version;
    private String requestId;
    private long timestamp;
    private List<Image> images;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {

        private String uid;
        private String name;
        private String inferResult;
        private String message;
        private List<Field> fields;
        private ValidationResult validationResult;

        @AllArgsConstructor
        @NoArgsConstructor
        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Field {

            private String valueType;
            private String inferText;
            private double inferConfidence;
            private String type;
            private boolean lineBreak;
            private BoundingPoly boundingPoly;

            @AllArgsConstructor
            @NoArgsConstructor
            @Setter
            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class BoundingPoly {

                private List<Vertex> vertices;

                public static class Vertex {

                    private double x;
                    private double y;

                }
            }
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        // 검증 결과를 담는 내부 클래스
        public static class ValidationResult {

            private String result;
        }
    }
}
