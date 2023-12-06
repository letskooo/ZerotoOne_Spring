package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pm2_5.studypartner.domain.Context;
import pm2_5.studypartner.domain.Keyword;

import java.util.List;

public interface ContextRepository extends JpaRepository<Context, Long> {
    @Query("SELECT c FROM Context c WHERE c.document.id = :documentId")
    List<Context> findContextsByDocumentId(@Param("documentId") Long documentId);

    @Modifying
    @Query("DELETE FROM Context c WHERE c.document.id = :documentId")
    void deleteContextsByDocumentId(@Param("documentId") Long documentId);
}
