package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Multiple extends BaseEntity {      // 객관식 문제

    // 객관식 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multiple_id")
    private Long id;

    // 문서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    // 객관식 제목
    private String title;

    // 객관식 문제
    private String question;

    // 중요도 체크
    private boolean checked;

    @OneToMany(mappedBy = "multiple", cascade = CascadeType.REMOVE)
    private List<MultipleChoice> multipleChoices;

    public Multiple(Document document, String title, String question, boolean checked){
        this.document = document;
        this.title = title;
        this.question = question;
        this.checked = checked;
    }
}
