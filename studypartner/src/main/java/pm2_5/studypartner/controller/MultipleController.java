package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.domain.Multiple;
import pm2_5.studypartner.dto.Multiple.MultipleDTO;
import pm2_5.studypartner.dto.Multiple.MultipleRespDTO;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.service.MultipleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/multiples")
@RequiredArgsConstructor
@Slf4j
public class MultipleController {
    private final MultipleService multipleService;

    // 키워드 생성 및 등록
    @PostMapping("/{documentId}")
    public MultipleRespDTO addMultiple(@PathVariable Long documentId) throws JsonProcessingException {

        return multipleService.registerMultiple(documentId);
    }

    @GetMapping("/result")
    public List<MultipleDTO> findAddResult(@RequestBody MultipleRespDTO multipleRespDTO){
        return multipleService.findRegisteredMultiples(multipleRespDTO);
    }

    @DeleteMapping("/result")
    public Map<String, Long> removeAddResult(@RequestBody MultipleRespDTO multipleRespDTO){
        multipleService.deleteRegisteredMultiples(multipleRespDTO);
        return Collections.singletonMap("documentId", multipleRespDTO.getDocumentId());
    }

    @GetMapping("")
    public MultipleDTO findMultiple(@RequestParam Long documentId, @RequestParam Long multipleId){
        return multipleService.findMultiple(documentId, multipleId);
    }

    @DeleteMapping("")
    public Map<String, Long> removeMultiple(@RequestParam Long documentId, @RequestParam Long multipleId){
        multipleService.deleteMultiple(documentId, multipleId);
        return Collections.singletonMap("documentId", documentId);
    }
}
