package pm2_5.studypartner.dto.written;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WrittenRespDTO {
    private Long documentId;
    private List<Long> writtenIds;
}
