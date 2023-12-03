package pm2_5.studypartner.dto.keyword;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pm2_5.studypartner.domain.Keyword;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KeywordsDTO {

    @JsonProperty("count")
    private int count;
    @JsonProperty("keywords")
    private List<KeywordDTO> keywords;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class KeywordDTO{

        @JsonProperty("keyword")
        private String keyword;
        @JsonProperty("description")
        private String description;
    }


}
