package pm2_5.studypartner.dto.Multiple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleRespDTO {
    private Long documentId;
    private List<Long> multipleIds;
}
