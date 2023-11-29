package pm2_5.studypartner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.auth.MemberJoinDTO;
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

    public void joinMember(MemberJoinDTO memberJoinDTO){

        Member newMember = new Member(
                memberJoinDTO.getUsername(),
                passwordEncoder.encode(memberJoinDTO.getPassword()),
                memberJoinDTO.getName(),
                memberJoinDTO.getEmail()
        );

        memberRepository.save(newMember);
    }

    public String checkIdDuplicate(String username){

        Optional<Member> result = memberRepository.findByUsername(username);

        if (result.isPresent()){

            return "exist";
        } else {
            return "available";
        }
    }
}
