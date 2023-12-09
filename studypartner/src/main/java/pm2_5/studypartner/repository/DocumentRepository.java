package pm2_5.studypartner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Member;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("select d from Document d where d.member = :member order by d.created desc")
    List<Document> findByMember(@Param("member") Member member);

    @EntityGraph(attributePaths = {"keywords", "contexts", "multiples", "writtens"})
    Optional<Document> findById(Long documentId);
}
