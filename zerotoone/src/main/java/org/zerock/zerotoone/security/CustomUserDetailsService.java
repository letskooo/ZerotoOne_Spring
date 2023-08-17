package org.zerock.zerotoone.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService { // 인증 처리 클래스

    // 실제 로그인 요청이 오면 자동으로 실행되는 메소드
    // 아이디만으로 유저 정보 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        log.info("loadUserByUsername: " + username);

        return null;

    }
}
