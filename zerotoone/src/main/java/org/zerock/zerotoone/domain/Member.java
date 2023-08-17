package org.zerock.zerotoone.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    private String memberId;
    private String memberPw;
    private int memberAge;
    private String memberName;
    private String memberSex;
    private String memberEmail;

    public void changePassword(String memberPw) {
        this.memberPw = memberPw;
    }
    public void changeEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

}
