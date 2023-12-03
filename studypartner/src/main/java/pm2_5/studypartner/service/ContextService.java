package pm2_5.studypartner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.repository.ContextRepository;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.KeywordRepository;
import pm2_5.studypartner.repository.MemberRepository;
import pm2_5.studypartner.util.OpenaiUtil;
import pm2_5.studypartner.util.PapagoUtil;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContextService {

    public final PapagoUtil papagoUtil;
    public final OpenaiUtil openaiUtil;
    public final ContextRepository contextRepository;
    public final DocumentRepository documentRepository;

    public void registerContext(Long documentId){
        
        // 해당 자료를 가져옴
        Document findDocument = documentRepository.findById(documentId).get();
        String translatedText = findDocument.getContent();
        
        // 역할 설정

        
        // chat gpt의 응답을 추출
    }
}
