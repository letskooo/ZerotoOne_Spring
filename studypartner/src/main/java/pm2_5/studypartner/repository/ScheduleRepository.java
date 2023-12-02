package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 월별 조회
    @Query("select s from Schedule s where s.member = :member and s.date between :start and :end")
    List<Schedule> findMonthlySchedule(@Param("member") Member member,
                                       @Param("start") LocalDate start,
                                       @Param("end") LocalDate end);

    // 일별 조회
    @Query("select s from Schedule s where s.member = :member and s.date =:date")
    List<Schedule> findDailySchedule(@Param("member") Member member,
                                     @Param("date") LocalDate date);
}
