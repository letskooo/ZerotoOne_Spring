package pm2_5.studypartner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.member.MemberDTO;
import pm2_5.studypartner.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    // 회원 정보 조회 메소드
    @GetMapping("/{username}")
    public MemberDTO getMember(@PathVariable String username){

        return memberService.getMember(username);

    }

    // 회원 정보 수정 메소드
    @PutMapping("")
    public void updateMember(@RequestBody MemberDTO memberDTO){

        memberService.modifyMember(memberDTO);
    }
}
