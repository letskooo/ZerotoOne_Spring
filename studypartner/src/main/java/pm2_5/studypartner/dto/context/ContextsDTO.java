package pm2_5.studypartner.dto.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContextsDTO {

    @JsonProperty("documentId")
    private Long documentId;
    @JsonProperty("count")
    private int count;
    @JsonProperty("contexts")
    private List<ContextDTO> contexts;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ContextDTO{

        @JsonProperty("content")
        private String content;
        @JsonProperty("summary")
        private String summary;
    }

    public ContextsDTO(int count) {
        this.count = count;
        this.contexts = new ArrayList<>();
    }

}
