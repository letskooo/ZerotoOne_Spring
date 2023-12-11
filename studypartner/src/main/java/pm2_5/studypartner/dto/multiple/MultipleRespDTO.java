package pm2_5.studypartner.dto.multiple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleRespDTO {
    private Long documentId;
    private List<Long> multipleIds;
}
