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

    @PostMapping("/addKeyword")
    public KeywordsDTO addKeyword(@RequestBody Map<String, Long> request) throws JsonProcessingException {
        Long documentId = request.get("documentId");
        return keywordService.registerKeyword(documentId);
    }

}
