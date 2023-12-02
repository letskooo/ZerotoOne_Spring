package pm2_5.studypartner.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.auth.UserDTO;
import pm2_5.studypartner.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findByUsername(username);

        Member member = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find username"));

        log.info("--------CustomUserDetailsService---------------");

        UserDTO userDTO = new UserDTO(
                member.getUsername(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        return userDTO;
    }
}
