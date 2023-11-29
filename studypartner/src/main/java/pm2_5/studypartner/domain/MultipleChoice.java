package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class MultipleChoice extends BaseEntity {    // 객관식 문제 보기

    // 객관식 문제 보기 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multipleChoice_id")
    private Long id;

    // 객관식 문제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multiple_id")
    private Multiple multiple;

    // 객관식 보기 번호
    private int number;

    // 객관식 보기 내용
    private String content;
}
