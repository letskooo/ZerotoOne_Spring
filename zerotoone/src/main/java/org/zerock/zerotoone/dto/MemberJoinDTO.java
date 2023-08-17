package org.zerock.zerotoone.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

    private String memberId;
    private String memberPw;
    private int memberAge;
    private String memberName;
    private String memberSex;
    private String memberEmail;

}
