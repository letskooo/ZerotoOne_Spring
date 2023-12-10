package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pm2_5.studypartner.domain.Multiple;

import java.util.List;

public interface MultipleRepository extends JpaRepository<Multiple, Long> {
    Multiple findByDocumentIdAndId(Long documentId, Long multipleId);

    void deleteByDocumentIdAndId(Long documentId, Long multipleId);

    List<Multiple> findByDocumentId(Long documentId);
}
