package pm2_5.studypartner.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.domain.Schedule;
import pm2_5.studypartner.dto.schedule.ScheduleDailyRespDTO;
import pm2_5.studypartner.dto.schedule.ScheduleDetailDTO;
import pm2_5.studypartner.dto.schedule.ScheduleRegisterDTO;
import pm2_5.studypartner.dto.schedule.ScheduleUpdateDTO;
import pm2_5.studypartner.error.ApiException;
import pm2_5.studypartner.error.MemberErrorStatus;
import pm2_5.studypartner.error.ScheduleErrorStatus;
import pm2_5.studypartner.repository.MemberRepository;
import pm2_5.studypartner.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    // 월별 일정 전체 조회
    public List<LocalDate> findScheduleInCurrentMonth(Long memberId, int year, int month){

        // 회원 조회 및 예외처리
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());
        List<Schedule> result = scheduleRepository.findMonthlySchedule(findMember, start, end);

        log.info("===========월별 일정 전체 조회============");

        return result.stream()
                .map(Schedule::getDate)
                .collect(Collectors.toList());
    }

    // 일별 일정 전체 조회
    public List<ScheduleDailyRespDTO> findDailySchedule(Long memberId, LocalDate localDate){

        // 회원 조회 및 예외 처리
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        List<Schedule> findSchedules = scheduleRepository.findDailySchedule(findMember, localDate);

        // 조회 결과를 ScheduleDailyRespDTO 의 List 로 변환
        List<ScheduleDailyRespDTO> dailyDTOList = findSchedules.stream()
                .map(schedule -> new ScheduleDailyRespDTO(schedule.getId(),
                        schedule.getTitle())).collect(Collectors.toList());

        log.info("==========일별 일정 전체 조회===========");

        return dailyDTOList;
    }

    // 일정 상세 조회
    public ScheduleDetailDTO findScheduleDetail(Long scheduleId){

        // 일정 조회 및 예외처리
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        Schedule findSchedule = schedule.orElseThrow(() -> new ApiException(ScheduleErrorStatus.SCHEDULE_NOT_FOUND));

        log.info("==========일정 상세 조회===========");

        return new ScheduleDetailDTO(findSchedule.getId(), findSchedule.getTitle(),
                findSchedule.getContent(), findSchedule.getDate());
    }

    // 일정 등록
    public void registerSchedule(ScheduleRegisterDTO scheduleRegisterDTO){

        // 회원 조회 및 예외 처리
        Optional<Member> member = memberRepository.findById(scheduleRegisterDTO.getMemberId());
        Member findMember = member.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        Schedule newSchedule = new Schedule(findMember, scheduleRegisterDTO.getScheduleTitle(),
                scheduleRegisterDTO.getScheduleContent(), scheduleRegisterDTO.getScheduleDate());

        log.info("===========일정 등록===========");

        scheduleRepository.save(newSchedule);
    }

    // 일정 수정
    public void modifySchedule(Long scheduleId, ScheduleUpdateDTO scheduleUpdateDTO){

        // 일정 조회 및 예외 처리
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        Schedule findSchedule = schedule.orElseThrow(() -> new ApiException(ScheduleErrorStatus.SCHEDULE_NOT_FOUND));

        // 수정 사항 적용
        findSchedule.setTitle(scheduleUpdateDTO.getScheduleTitle());

        if (scheduleUpdateDTO.getScheduleContent() != null){
            findSchedule.setContent(scheduleUpdateDTO.getScheduleContent());
        }
        findSchedule.setDate(scheduleUpdateDTO.getScheduleDate());

        log.info("==============일정 수정==============");
    }

    // 일정 삭제
    public void deleteSchedule(Long scheduleId){

        log.info("=============일정 삭제==============");

        scheduleRepository.deleteById(scheduleId);
    }
}
