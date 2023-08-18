package org.zerock.zerotoone.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.zerotoone.domain.Member;
import org.zerock.zerotoone.dto.MemberLoginDTO;
import org.zerock.zerotoone.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {   // 인증 처리 클래스

    private final MemberRepository memberRepository;

    // 로그인 요청이 오면 자동으로 실행되는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 로그인 요청 시 username 조회
        Optional<Member> result = memberRepository.findById(username);

        // 성공 시 반환, 실패 시 예외 처리
        Member member = result.orElseThrow(() -> new UsernameNotFoundException("can't find userId"));

        log.info("----------MemberDetailsService Member-----------");

        // DTO 객체 생성
        // USER 역할 부여
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO(
                member.getUserId(),
                member.getUserPw(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        log.info(memberLoginDTO);

        return memberLoginDTO;
    }
}
