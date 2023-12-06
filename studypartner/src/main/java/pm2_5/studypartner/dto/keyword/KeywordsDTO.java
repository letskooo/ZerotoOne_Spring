package pm2_5.studypartner.dto.keyword;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pm2_5.studypartner.domain.Keyword;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeywordsDTO {

    @JsonProperty("documentId")
    private Long documentId;
    @JsonProperty("count")
    private int count;
    @JsonProperty("keywords")
    private List<KeywordDTO> keywords;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class KeywordDTO{

        @JsonProperty("keyword")
        private String keyword;
        @JsonProperty("description")
        private String description;
    }

    public KeywordsDTO(int count) {
        this.count = count;
        this.keywords = new ArrayList<>();
    }

}
