package pm2_5.studypartner.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String sex;
    private int age;

    public Member(String username, String password, String name, String email, String sex, int age){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age = age;
    }

    // 비밀번호 변경 메소드
    public void changePassword(String password){
        this.password = password;
    }
}
