package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Written extends BaseEntity {      // 주관식 문제

    // 주관식 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "written_id")
    private Long id;

    // 문서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    // 주관식 제목
    private String title;

    // 주관식 문제
    private String question;

    // 주관식 답
    @Column(columnDefinition = "TEXT")
    private String answer;

    public Written(Document document, String title, String question, String answer){
        this.document = document;
        this.title = title;
        this.question = question;
        this.answer = answer;
    }
}
