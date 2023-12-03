package pm2_5.studypartner.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.schedule.*;
import pm2_5.studypartner.error.ApiException;
import pm2_5.studypartner.error.ScheduleErrorStatus;
import pm2_5.studypartner.service.ScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 월별 일정 전체 조회
    @GetMapping("/monthly")
    public List<LocalDate> getScheduleInCurrentMonth(@RequestParam Long memberId,
                                                     @RequestParam int year, @RequestParam int month){

        return scheduleService.findScheduleInCurrentMonth(memberId, year, month);
    }

    // 일별 일정 전체 조회
    @GetMapping("/daily")
    public Result getDailySchedules(@RequestParam Long memberId, @RequestParam String date){

        List<ScheduleDailyRespDTO> scheduleDailyDTOList =
                scheduleService.findDailySchedule(memberId, LocalDate.parse(date));

        return new Result(scheduleDailyDTOList.size(), scheduleDailyDTOList);
    }

    // 일정 상세 조회
    @GetMapping("/{scheduleId}")
    public ScheduleDetailDTO getSchedule(@PathVariable Long scheduleId){

        return scheduleService.findScheduleDetail(scheduleId);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    // 일정 등록
    @PostMapping("")
    public void addSchedule(@Valid @RequestBody ScheduleRegisterDTO scheduleRegisterDTO, BindingResult result){

        // 입력값 검증 처리
        List<String> messages = new ArrayList<>();
        if (result.hasErrors()){
            result.getAllErrors().stream().forEach(objectError -> messages.add(objectError.getDefaultMessage()));

            throw new ApiException(ScheduleErrorStatus.VALIDATION_ERROR, StringUtils.join(messages, ','));
        }

        scheduleService.registerSchedule(scheduleRegisterDTO);
    }

    // 일정 수정
    @PutMapping("/{scheduleId}")
    public void updateSchedule(@PathVariable Long scheduleId,
                               @Valid @RequestBody ScheduleUpdateDTO scheduleUpdateDTO, BindingResult result){

        // 입력값 검증 처리
        List<String> messages = new ArrayList<>();
        if (result.hasErrors()){
            result.getAllErrors().stream().forEach(objectError -> messages.add(objectError.getDefaultMessage()));

            throw new ApiException(ScheduleErrorStatus.VALIDATION_ERROR, StringUtils.join(messages, ','));
        }

        scheduleService.modifySchedule(scheduleId, scheduleUpdateDTO);
    }

    // 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public void removeSchedule(@PathVariable Long scheduleId){

        scheduleService.deleteSchedule(scheduleId);
    }
}