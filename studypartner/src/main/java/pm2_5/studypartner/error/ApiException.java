package pm2_5.studypartner.error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private MemberErrorStatus memberStatus;
    private ScheduleErrorStatus scheduleErrorStatus;

    public ApiException(MemberErrorStatus memberStatus){

        super(memberStatus.getMessage());
        this.memberStatus = memberStatus;
    }
    public ApiException(MemberErrorStatus memberStatus, String message) {

        super(message);
        this.memberStatus = memberStatus;
    }

    public ApiException(ScheduleErrorStatus scheduleErrorStatus) {
        super(scheduleErrorStatus.getMessage());
        this.scheduleErrorStatus = scheduleErrorStatus;
    }

    public ApiException(ScheduleErrorStatus scheduleErrorStatus, String message){
        super(message);
        this.scheduleErrorStatus = scheduleErrorStatus;
    }
}
