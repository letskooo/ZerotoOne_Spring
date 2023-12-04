package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pm2_5.studypartner.dto.context.ContextsDTO;
import pm2_5.studypartner.service.ContextService;

import java.util.Map;

@RestController
@RequestMapping("/api/contexts")
@RequiredArgsConstructor
@Slf4j
public class ContextController {
    private final ContextService contextService;

    @PostMapping("/addContext")
    public ContextsDTO addContext(@RequestBody Map<String, Long> request) throws JsonProcessingException {
        Long documentId = request.get("documentId");
        return contextService.registerContext(documentId);
    }
}
