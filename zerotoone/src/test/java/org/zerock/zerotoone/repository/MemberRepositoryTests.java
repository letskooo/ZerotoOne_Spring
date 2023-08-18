package org.zerock.zerotoone.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.zerotoone.domain.Member;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    // 멤버 생성 테스트
    @Test
    public void testInsert(){

        IntStream.rangeClosed(1,10).forEach(i -> {
            Member member = Member.builder()
                    .userId("Member" + i)
                    .userPw(passwordEncoder.encode("111" + i))
                    .userEmail("email"+i+"@test.com")
                    .build();
            memberRepository.save(member);
        });
    }
}
