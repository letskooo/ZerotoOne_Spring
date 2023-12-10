package pm2_5.studypartner.dto.Multiple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipleDTO {

    private Long documentId;
    private String title;
    private String question;
    private List<ChoicesDTO> multipleChoices;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ChoicesDTO{
        private String content;
        private int number;
        private boolean answer;

        public boolean getAnswer(){
            return this.answer;
        }
    }
}
