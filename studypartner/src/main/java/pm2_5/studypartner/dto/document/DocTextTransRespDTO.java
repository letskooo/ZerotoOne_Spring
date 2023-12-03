package pm2_5.studypartner.dto.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocTextTransRespDTO {
    // papago text translation api 응답 DTO

    private Message message;

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Message {
        private Result result;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Result {
        private String srcLangType;
        private String tarLangType;
        private String translatedText;
    }

}