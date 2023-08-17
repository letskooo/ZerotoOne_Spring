package org.zerock.zerotoone.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.zerotoone.domain.Member;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1, 20).forEach(i -> {

            // builder 패턴으로 객체 생성
            Member member = Member.builder()
                    .memberId("member" + i)
                    .memberPw("111" + i)
                    .memberEmail("email"+i+"@test.com")
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void readMembers() {

        String memberId = "member1";

        Optional<Member> result = memberRepository.findById(memberId);

        Member member = result.orElseThrow();

        log.info(member);

    }
}
