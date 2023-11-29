package pm2_5.studypartner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.member.MemberDTO;
import pm2_5.studypartner.dto.member.MemberJoinDTO;
import pm2_5.studypartner.error.ApiException;
import pm2_5.studypartner.error.member.MemberErrorStatus;
import pm2_5.studypartner.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 조회 메소드
    public MemberDTO getMember(String username) throws ApiException {

        Optional<Member> result = memberRepository.findByUsername(username);

        Member findMember = result.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        return new MemberDTO(findMember.getUsername(), findMember.getName(), findMember.getEmail());
    }

    // 회원 추가 메소드
    public void addMember(MemberJoinDTO memberJoinDTO){

        Member newMember = new Member(
                memberJoinDTO.getUsername(),
                passwordEncoder.encode(memberJoinDTO.getPassword()),
                memberJoinDTO.getName(),
                memberJoinDTO.getEmail()
        );

        memberRepository.save(newMember);
    }

    // 회원 정보 수정 메소드
    public void modifyMember(MemberDTO memberDTO){

        Optional<Member> result = memberRepository.findByUsername(memberDTO.getUsername());

        Member findMember = result.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        findMember.updateMember(memberDTO.getName(), memberDTO.getEmail());
    }


    // 중복 체크 메소드
    public String checkIdDuplicate(String username){

        Optional<Member> result = memberRepository.findByUsername(username);

        if (result.isPresent()){

            return "exist";
        } else {
            return "available";
        }
    }
}
