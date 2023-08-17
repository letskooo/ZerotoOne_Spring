package org.zerock.zerotoone.service;

import org.zerock.zerotoone.dto.MemberJoinDTO;
import org.zerock.zerotoone.dto.MemberLoginDTO;

public interface MemberService {

    void join(MemberJoinDTO memberJoinDTO);

    void login(MemberLoginDTO memberLoginDTO);

}
