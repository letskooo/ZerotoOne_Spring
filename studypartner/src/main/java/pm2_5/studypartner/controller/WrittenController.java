package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.written.WrittenDTO;
import pm2_5.studypartner.dto.written.WrittenReqDTO;
import pm2_5.studypartner.dto.written.WrittenRespDTO;
import pm2_5.studypartner.service.WrittenService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/writtens")
public class WrittenController {

    private final WrittenService writtenService;

    // 키워드 생성 및 등록
    @PostMapping("/{documentId}")
    public WrittenRespDTO addWritten(@PathVariable Long documentId) throws JsonProcessingException {

        return writtenService.registerWritten(documentId);
    }

    @GetMapping("/result")
    public List<WrittenDTO> findAddResult(@RequestParam Long documentId, @RequestParam List<Long> writtenIds){
        return writtenService.findRegisteredWrittens(documentId, writtenIds);
    }

    @DeleteMapping("/result")
    public Map<String, Long> removeAddResult(@RequestBody WrittenRespDTO writtenRespDTO){
        writtenService.deleteRegisteredWrittens(writtenRespDTO);
        return Collections.singletonMap("documentId", writtenRespDTO.getDocumentId());
    }

    @GetMapping("")
    public WrittenDTO findMultiple(@RequestParam Long documentId, @RequestParam Long writtenId){
        return writtenService.findWritten(documentId, writtenId);
    }

    @DeleteMapping("")
    public Map<String, Long> removeMultiple(@RequestBody WrittenReqDTO writtenReqDTO){
        writtenService.deleteWritten(writtenReqDTO.getDocumentId(), writtenReqDTO.getWrittenId());
        return Collections.singletonMap("documentId", writtenReqDTO.getDocumentId());
    }
}
