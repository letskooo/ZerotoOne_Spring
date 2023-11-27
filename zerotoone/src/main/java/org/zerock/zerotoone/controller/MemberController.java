package org.zerock.zerotoone.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.zerotoone.domain.Member;
import org.zerock.zerotoone.dto.MemberLoginDTO;
import org.zerock.zerotoone.security.MemberDetailsService;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberDetailsService memberDetailsService;

    @PostMapping("check")
    public void checkPOST(MemberLoginDTO memberLoginDTO){

        String username = memberLoginDTO.getUsername();
        String password = memberLoginDTO.getPassword();

        log.info("username: " + username);
        log.info("password: " + password);
    }



}
