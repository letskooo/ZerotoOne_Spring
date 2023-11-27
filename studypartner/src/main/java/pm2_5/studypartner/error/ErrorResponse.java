package pm2_5.studypartner.error;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private final String path;
    private final String error;
    private final String message;
}
