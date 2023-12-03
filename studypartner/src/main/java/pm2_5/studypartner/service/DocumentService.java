package pm2_5.studypartner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocTextTransReqDTO;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.MemberRepository;
import pm2_5.studypartner.util.PapagoUtil;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;

    private final PapagoUtil papagoUtil;

    public Long registerTransDoc(DocTextTransReqDTO docTextTransReqDTO) {
        
        Member findMember = memberRepository.findById(docTextTransReqDTO.getMemberId()).get();

        // papago에게 text 번역 요청하여 번역된 텍스트 반환
        String translatedText = papagoUtil.translateText(docTextTransReqDTO);

        // 번역된 text 기반으로 자료 저장
        Document document = new Document(findMember, translatedText);
        document = documentRepository.save(document);

        return document.getId();
    }


    public Long registerImgTransDoc(DocImgTransReqDTO docImgTransReqDTO) throws IOException {
        Member findMember = memberRepository.findById(docImgTransReqDTO.getMemberId()).get();

        String translated = papagoUtil.translateImg(docImgTransReqDTO);

        Document document = new Document(findMember, translated);
        document =documentRepository.save(document);

        return document.getId();
    }

}
