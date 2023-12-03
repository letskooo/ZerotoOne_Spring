package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pm2_5.studypartner.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
