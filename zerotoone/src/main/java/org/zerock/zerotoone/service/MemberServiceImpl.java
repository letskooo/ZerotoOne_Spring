package org.zerock.zerotoone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.zerotoone.domain.Member;
import org.zerock.zerotoone.dto.MemberJoinDTO;
import org.zerock.zerotoone.repository.MemberRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Override
    public void join(MemberJoinDTO memberJoinDTO){

        Member member = modelMapper.map(memberJoinDTO, Member.class);

        log.info("==========");
        log.info("member");
        memberRepository.save(member);
    }
}
