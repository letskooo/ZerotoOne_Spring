package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule extends BaseEntity {      // 일정

    // 일정 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    // 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 일정 제목
    private String title;

    // 일정 내용
    private String content;

    // 일정 날짜
    private LocalDate date;

    public Schedule(Member member, String title, String content, LocalDate date) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
