package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Written extends BaseEntity {      // 주관식 문제

    // 주관식 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writted_id")
    private Long id;

    // 문서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    // 주관식 문제
    private String question;

    // 주관식 답
    private String answer;

    // 중요도 체크
    private boolean checked;
}
