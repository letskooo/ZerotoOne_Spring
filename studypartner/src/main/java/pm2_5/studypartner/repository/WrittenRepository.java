package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pm2_5.studypartner.domain.Multiple;
import pm2_5.studypartner.domain.Written;

import java.util.List;

public interface WrittenRepository extends JpaRepository<Written, Long> {

    List<Written> findByDocumentId(Long documentId);
}
