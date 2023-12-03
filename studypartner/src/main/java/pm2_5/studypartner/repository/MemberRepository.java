package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pm2_5.studypartner.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원 이름으로 조회
    Optional<Member> findByUsername(@Param("username") String username);
}
