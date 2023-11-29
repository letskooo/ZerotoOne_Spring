package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Document extends BaseEntity {      // 생성 자료

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;
}
