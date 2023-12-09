package pm2_5.studypartner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.document.DocumentDTO;
import pm2_5.studypartner.dto.document.MainScreenDTO;
import pm2_5.studypartner.dto.papago.ImgTransReqDTO;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.MemberRepository;
import pm2_5.studypartner.util.NaverCloudUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;

    private final NaverCloudUtil naverCloudUtil;

    // 문서 내역 조회
    public MainScreenDTO findDocumentList(Long memberId){

        Member findMember = memberRepository.findById(memberId).get();

        List<Document> findDocuments = documentRepository.findByMember(findMember);

        List<MainScreenDTO.DocumentListDTO> documentList = findDocuments.stream()
                .map(document -> new MainScreenDTO.DocumentListDTO(document.getId(), document.getTitle(),
                        document.getCreated().toLocalDate())).collect(Collectors.toList());

        log.info("================문서 내역 조회=================");

        return new MainScreenDTO(findMember.getName(), documentList.size(), documentList);
    }

    // 텍스트 번역 및 저장
    public Long registerTextTransDoc(TextTransReqDTO textTransReqDTO) throws JsonProcessingException {
        
        Member findMember = memberRepository.findById(textTransReqDTO.getMemberId()).get();

        // papago에게 text 번역 요청하여 번역된 텍스트 반환
        String translatedText = naverCloudUtil.translateText(textTransReqDTO);

        // 번역된 text 기반으로 자료 저장
        Document document = new Document(textTransReqDTO.getDocumentTitle(), findMember, textTransReqDTO.getText(), translatedText);
        document = documentRepository.save(document);

        return document.getId();
    }

    public String imgOCR(ImgTransReqDTO imgTransReqDTO) throws IOException {
        return naverCloudUtil.extractText(imgTransReqDTO);
    }

    // 문서 조회
    public DocumentDTO findDocument(Long documentId) {
        // 회원 조회
        Document findDocument = documentRepository.findById(documentId).get();

        log.info("===========문서 조회============");

        return new DocumentDTO(findDocument.getTitle(), findDocument.getKoContent());
    }

    // 문서 삭제
    public void deleteDocument(Long documentId){
        documentRepository.deleteById(documentId);
    }
}
