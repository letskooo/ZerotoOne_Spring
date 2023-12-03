package pm2_5.studypartner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Keyword;
import pm2_5.studypartner.dto.document.DocTextTransReqDTO;
import pm2_5.studypartner.dto.document.DocTextTransRespDTO;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.dto.keyword.OpenaiRespDTO;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.repository.KeywordRepository;
import pm2_5.studypartner.util.OpenaiUtil;
import pm2_5.studypartner.util.PapagoUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void registerKeyword(Long documentId) throws JsonProcessingException {

        Document findDocument = documentRepository.findById(documentId).get();

        String translateText = findDocument.getContent();

        String json = openaiUtil.extractContent(translateText);

        ObjectMapper objectMapper = new ObjectMapper();
        KeywordsDTO keywordsDTO = objectMapper.readValue(json, KeywordsDTO.class);

        for(KeywordsDTO.KeywordDTO keword : keywordsDTO.getKeywords()) {
            System.out.println(keword.toString());
            DocTextTransReqDTO docTextTransReqDTO = new DocTextTransReqDTO(documentId, "en", "ko", keword.getKeyword());
            String translateKeyword = papagoUtil.translateText(docTextTransReqDTO);
            docTextTransReqDTO.setText(keword.getDescription());
            String translateDesc = papagoUtil.translateText(docTextTransReqDTO);
            Keyword newKeyword = new Keyword(findDocument ,keword.getKeyword() + "\n (" + translateKeyword + ")", translateDesc);

            keywordRepository.save(newKeyword);
        }

    }


}
