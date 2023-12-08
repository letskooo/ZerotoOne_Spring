package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.domain.Keyword;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.service.KeywordService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
@Slf4j
public class KeywordController {
    private final KeywordService keywordService;

    // 키워드 생성 및 등록
    @PostMapping("/{documentId}")
    public KeywordsDTO addKeyword(@PathVariable Long documentId) throws JsonProcessingException {

        return keywordService.registerKeywords(documentId);
    }

    // 키워드 조회
    @GetMapping("/{documentId}")
    public KeywordsDTO getKeywords(@PathVariable Long documentId) {

        return keywordService.findKeywords(documentId);
    }

    // 키워드 삭제
    @DeleteMapping("/{documentId}")
    public Map<String, Long> removeKeywords(@PathVariable Long documentId) {

        keywordService.deleteKeywords(documentId);
        return Collections.singletonMap("documentId", documentId);
    }
}
