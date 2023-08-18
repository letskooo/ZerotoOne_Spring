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
public class Member {

    @Id
    private String userId;
    private String userPw;

    public void changePw(String userPw){
        this.userPw = userPw;
    }
}
