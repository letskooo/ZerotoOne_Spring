package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // 문서 제목
    private String title;

    // 한글 문서 내용
    @Column(columnDefinition = "TEXT")
    private String koContent;

    // 영어 문서 내용
    @Column(columnDefinition = "TEXT")
    private String enContent;

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    private List<Context> contexts = new ArrayList<>();

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    private List<Multiple> multiples = new ArrayList<>();

    @OneToMany(mappedBy = "document", cascade = CascadeType.REMOVE)
    private List<Written> writtens = new ArrayList<>();

    public Document(String title, Member member, String koContent, String enContent) {
        this.title = title;
        this.member = member;
        this.koContent = koContent;
        this.enContent = enContent;
    }
}
