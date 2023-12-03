package pm2_5.studypartner.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.member.MemberDTO;
import pm2_5.studypartner.dto.member.MemberJoinDTO;
import pm2_5.studypartner.dto.member.MemberUpdateDTO;
import pm2_5.studypartner.error.ApiException;
import pm2_5.studypartner.error.MemberErrorStatus;
import pm2_5.studypartner.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 조회
    public MemberDTO findMember(Long memberId) throws ApiException {

        // 회원 조회 및 예외 처리
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        log.info("===========회원 조회============");

        return new MemberDTO(findMember.getUsername(), findMember.getName(), findMember.getEmail());
    }

    // 회원 등록
    public void registerMember(MemberJoinDTO memberJoinDTO){

        // 새로운 회원 객체 생성
        Member newMember = new Member(
                memberJoinDTO.getUsername(),
                passwordEncoder.encode(memberJoinDTO.getPassword()),
                memberJoinDTO.getName(),
                memberJoinDTO.getEmail()
        );

        memberRepository.save(newMember);

        log.info("==========회원 추가==========");
    }

    // 회원 정보 수정 메소드
    public void modifyMember(Long memberId, MemberUpdateDTO memberUpdateDTO){

        // 회원 조회 및 에러 처리
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember = member.orElseThrow(() -> new ApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        // 수정 사항 적용
        findMember.updateMember(memberUpdateDTO.getName(), memberUpdateDTO.getEmail());

        log.info("==========회원 정보 수정============");
    }


    // 중복 체크
    public String checkIdDuplicate(String username){

        Optional<Member> result = memberRepository.findByUsername(username);

        log.info("=============중복 체크=============");

        if (result.isPresent()){

            return "exist";
        } else {
            return "available";
        }
    }
}
