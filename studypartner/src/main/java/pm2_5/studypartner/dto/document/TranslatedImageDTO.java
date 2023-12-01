package pm2_5.studypartner.dto.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TranslatedImageDTO {

    private Data data;


    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Data {

        @JsonProperty("sourceLang")
        private String sourceLang;

        @JsonProperty("targetLang")
        private String targetLang;

        @JsonProperty("sourceText")
        private String sourceText;

        @JsonProperty("targetText")
        private String targetText;

        @JsonProperty("blocks")
        private List<Block> blocks;


        @AllArgsConstructor
        @Getter
        @NoArgsConstructor
        public static class Block {

            @JsonProperty("sourceLang")
            private String sourceLang;

            @JsonProperty("sourceText")
            private String sourceText;

            @JsonProperty("targetText")
            private String targetText;

            @JsonProperty("lb")
            private Coordinates lb;

            @JsonProperty("lt")
            private Coordinates lt;

            @JsonProperty("rb")
            private Coordinates rb;

            @JsonProperty("rt")
            private Coordinates rt;

            @JsonProperty("lines")
            private List<Line> lines;

        }

        @AllArgsConstructor
        @Getter
        @NoArgsConstructor
        public static class Line {

            @JsonProperty("lb")
            private Coordinates lb;

            @JsonProperty("lt")
            private Coordinates lt;

            @JsonProperty("rb")
            private Coordinates rb;

            @JsonProperty("rt")
            private Coordinates rt;

            @JsonProperty("words")
            private List<Word> words;

        }

        @AllArgsConstructor
        @Getter
        @NoArgsConstructor
        public static class Word {

            @JsonProperty("sourceText")
            private String sourceText;

            @JsonProperty("lb")
            private Coordinates lb;

            @JsonProperty("lt")
            private Coordinates lt;

            @JsonProperty("rb")
            private Coordinates rb;

            @JsonProperty("rt")
            private Coordinates rt;

        }

        @AllArgsConstructor
        @Getter
        @NoArgsConstructor
        public static class Coordinates {

            private int x;
            private int y;

            // Getter and Setter methods
        }
    }
}
