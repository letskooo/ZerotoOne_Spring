package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.context.ContextsDTO;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.service.ContextService;

import java.util.Map;

@RestController
@RequestMapping("/api/contexts")
@RequiredArgsConstructor
@Slf4j
public class ContextController {
    private final ContextService contextService;

    @PostMapping("")
    public ContextsDTO addContexts(@RequestBody Map<String, Long> request) throws JsonProcessingException {
        Long documentId = request.get("documentId");
        return contextService.registerContext(documentId);
    }

    @GetMapping("")
    public ContextsDTO getContexts(@RequestBody Map<String, Long> request) {
        Long documentId = request.get("documentId");
        return contextService.findContexts(documentId);
    }

    // 키워드 삭제
    @DeleteMapping("")
    public Map<String, Long> removeContexts(@RequestBody Map<String, Long> request) {
        Long documentId = request.get("documentId");
        contextService.deleteContexts(documentId);
        return request;
    }
}
