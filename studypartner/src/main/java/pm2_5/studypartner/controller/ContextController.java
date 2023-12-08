package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.context.ContextsDTO;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.service.ContextService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/contexts")
@RequiredArgsConstructor
@Slf4j
public class ContextController {
    private final ContextService contextService;

    @PostMapping("/{documentId}")
    public ContextsDTO addContexts(@PathVariable Long documentId) throws JsonProcessingException {

        return contextService.registerContext(documentId);
    }

    @GetMapping("/{documentId}")
    public ContextsDTO getContexts(@PathVariable Long documentId) {

        return contextService.findContexts(documentId);
    }

    // 키워드 삭제
    @DeleteMapping("/{documentId}")
    public Map<String, Long> removeContexts(@PathVariable Long documentId) {
        contextService.deleteContexts(documentId);
        return Collections.singletonMap("documentId", documentId);
    }
}
