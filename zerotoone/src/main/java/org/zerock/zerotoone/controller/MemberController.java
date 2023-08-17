package org.zerock.zerotoone.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.zerotoone.dto.MemberJoinDTO;
import org.zerock.zerotoone.dto.MemberLoginDTO;
import org.zerock.zerotoone.service.MemberService;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String joinPOST(@RequestBody MemberJoinDTO memberJoinDTO){

        memberService.join(memberJoinDTO);

        return "ok";

    }
}
