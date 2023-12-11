package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import pm2_5.studypartner.dto.multiple.MultipleDTO;
import pm2_5.studypartner.dto.multiple.MultipleReqDTO;
import pm2_5.studypartner.dto.multiple.MultipleRespDTO;
import pm2_5.studypartner.dto.multiple.MultiplesFindDTO;
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

    @GetMapping("/{documentId}")
    public List<MultiplesFindDTO> getMultipleList(@PathVariable Long documentId) {

        return multipleService.findMultipleList(documentId);
    }

    // 키워드 생성 및 등록
    @PostMapping("/{documentId}")
    public MultipleRespDTO addMultiple(@PathVariable Long documentId) throws JsonProcessingException {

        return multipleService.registerMultiple(documentId);
    }

    @GetMapping("/result")
    public List<MultipleDTO> findAddResult(@RequestParam Long documentId, @RequestParam List<Long> multipleIds){
        return multipleService.findRegisteredMultiples(documentId, multipleIds);
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
    public Map<String, Long> removeMultiple(@RequestBody MultipleReqDTO multipleReqDTO){
        multipleService.deleteMultiple(multipleReqDTO.getDocumentId(), multipleReqDTO.getMultipleId());
        return Collections.singletonMap("documentId", multipleReqDTO.getDocumentId());
    }
}
