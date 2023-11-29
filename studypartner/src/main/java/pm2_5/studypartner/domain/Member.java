package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {    // 회원

    // 회원 식별 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    // 회원 로그인 아이디
    private String username;

    // 회원 로그인 비밀번호
    private String password;

    // 회원 이름
    private String name;

    // 회원 이메일
    private String email;

    public Member(String username, String password, String name, String email){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // 비밀번호 변경 메소드
    public void changePassword(String password){
        this.password = password;
    }
}
