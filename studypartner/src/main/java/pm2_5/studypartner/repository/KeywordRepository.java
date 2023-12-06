package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pm2_5.studypartner.domain.Keyword;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    @Query("SELECT k FROM Keyword k WHERE k.document.id = :documentId")
    List<Keyword> findKeywordsByDocumentId(@Param("documentId") Long documentId);

    @Modifying
    @Query("DELETE FROM Keyword k WHERE k.document.id = :documentId")
    void deleteKeywordsByDocumentId(@Param("documentId") Long documentId);
}
