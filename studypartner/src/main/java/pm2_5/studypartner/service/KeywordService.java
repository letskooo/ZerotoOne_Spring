package pm2_5.studypartner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Keyword;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.KeywordRepository;
import pm2_5.studypartner.util.OpenaiUtil;
import pm2_5.studypartner.util.PapagoUtil;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KeywordService {

    public final PapagoUtil papagoUtil;
    public final OpenaiUtil openaiUtil;
    public final KeywordRepository keywordRepository;
    public final DocumentRepository documentRepository;

    // 키워드 등록
    public KeywordsDTO registerKeyword(Long documentId) throws JsonProcessingException {
        
        // 해당 자료를 가져옴
        Document findDocument = documentRepository.findById(documentId).get();
        String translatedText = findDocument.getContent();

        // 역할 설정
        String system = """
                You need to extract important keywords and descriptions of those keywords based on the material I've given you.
                I'll give you the steps, and you'll follow them to extract the keywords and descriptions.
                1. Go through the material I've given you and extract the keywords you think are important.
                2. generate the descriptions for those keywords from the source material.
                3. count the number of keywords and let me know key count
                4. pair those keywords with their descriptions and get back to me only a JSON style response that jackson can parse. I'll give you an example response format in ```.
                
                ```{"count" : 2, "keywords" : [{"keyword":"key", "description":"desc"},{"keyword":"key", "description":"desc"}]}```
                """;

        // chat gpt의 응답을 추출
        String json = openaiUtil.extractContent(system, translatedText);

        // chat gpt의 응답을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        KeywordsDTO keywordsDTO = objectMapper.readValue(json, KeywordsDTO.class);

        KeywordsDTO newKeywords = new KeywordsDTO(keywordsDTO.getCount());

        // 각 keyword를 확인 및 저장
        for(KeywordsDTO.KeywordDTO keyword : keywordsDTO.getKeywords()) {
            // 키워드와 설명 번역
            TextTransReqDTO textTransReqDTO = new TextTransReqDTO(documentId, "en", "ko", keyword.getKeyword());
            String translateKeyword = papagoUtil.translateText(textTransReqDTO);
            textTransReqDTO.setText(keyword.getDescription());
            String translateDesc = papagoUtil.translateText(textTransReqDTO);
            
            // 키워드 저장
            Keyword newKeyword = new Keyword(findDocument ,keyword.getKeyword() + "\n (" + translateKeyword + ")", translateDesc);
            keywordRepository.save(newKeyword);

            newKeywords.getKeywords().add(new KeywordsDTO.KeywordDTO(newKeyword.getKeyword(), newKeyword.getDescription()));
        }

        return newKeywords;

    }


}
