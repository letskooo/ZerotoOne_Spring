package pm2_5.studypartner.error;

import lombok.Getter;
import pm2_5.studypartner.error.member.MemberErrorStatus;

@Getter
public class ApiException extends RuntimeException {

    private MemberErrorStatus status;

    public ApiException(MemberErrorStatus status){

        super(status.getMessage());
        this.status = status;
    }
    public ApiException(MemberErrorStatus status, String message) {

        super(message);
        this.status = status;
    }
}
