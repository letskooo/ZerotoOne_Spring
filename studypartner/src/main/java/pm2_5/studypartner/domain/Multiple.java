package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Multiple extends BaseEntity {      // 객관식 문제

    // 객관식 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multiple_id")
    private Long id;

    // 문서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    // 객관식 문제
    private String question;

    // 객관식 문제
    private int answer;

    // 중요도 체크
    private boolean checked;

    @OneToMany(mappedBy = "multiple", cascade = CascadeType.REMOVE)
    private List<MultipleChoice> multipleChoices;
}
