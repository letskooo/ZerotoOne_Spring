package pm2_5.studypartner.dto.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class OpenaiRespDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created")
    private long created;

    @JsonProperty("model")
    private String model;

    @JsonProperty("usage")
    private Usage usage;

    @JsonProperty("choices")
    private Choice[] choices;


    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;

    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Choice {
        @JsonProperty("message")
        private Message message;

        @JsonProperty("finish_reason")
        private String finishReason;

        @JsonProperty("logprobs")
        private String logprobs;

        @JsonProperty("index")
        private int index;

    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Message {
        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;

    }

}


