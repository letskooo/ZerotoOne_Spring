package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.domain.Keyword;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.service.KeywordService;

import java.util.Map;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
@Slf4j
public class KeywordController {
    private final KeywordService keywordService;

    // 키워드 생성 및 등록
    @PostMapping("")
    public KeywordsDTO addKeyword(@RequestBody Map<String, Long> request) throws JsonProcessingException {
        Long documentId = request.get("documentId");
        return keywordService.registerKeywords(documentId);
    }

    // 키워드 조회
    @GetMapping("")
    public KeywordsDTO getKeywords(@RequestBody Map<String, Long> request) {
        Long documentId = request.get("documentId");
        return keywordService.findKeywords(documentId);
    }

    // 키워드 삭제
    @DeleteMapping("")
    public Map<String, Long> removeKeywords(@RequestBody Map<String, Long> request) {
        Long documentId = request.get("documentId");
        keywordService.deleteKeywords(documentId);
        return request;
    }

}
