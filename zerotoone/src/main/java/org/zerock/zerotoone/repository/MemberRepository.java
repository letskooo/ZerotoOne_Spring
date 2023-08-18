package org.zerock.zerotoone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.zerotoone.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
