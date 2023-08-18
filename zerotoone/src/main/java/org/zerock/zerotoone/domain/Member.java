package org.zerock.zerotoone.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {

    @Id
    private String userId;
    private String userPw;
    private String userEmail;

    public void changePw(String userPw){
        this.userPw = userPw;
    }

    public void changeEmail(String userEmail){
        this.userEmail = userEmail;
    }
}
