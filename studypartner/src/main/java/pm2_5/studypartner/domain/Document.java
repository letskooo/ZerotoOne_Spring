package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity {      // 생성 문서

    // 문서 식별 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 문서 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    public Document(Member member, String content) {
        this.member = member;
        this.content = content;
    }
}
