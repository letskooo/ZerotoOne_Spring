package pm2_5.studypartner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocTextTransReqDTO;
import pm2_5.studypartner.service.DocumentService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/textTrans")
    public Map<String, Long> addTransDoc(@ModelAttribute DocTextTransReqDTO docTextTransReqDTO){

        Map<String, Long> documentMap = new HashMap<>();
        documentMap.put("documentId", documentService.registerTransDoc(docTextTransReqDTO));
        return documentMap;
    }

    @PostMapping(value = "/imgTrans")
    public Map<String, Long> addTransImgDoc(@ModelAttribute DocImgTransReqDTO docImgTransReqDTO) throws IOException {

        Map<String, Long> documentMap = new HashMap<>();
        documentMap.put("documentId", documentService.registerImgTransDoc(docImgTransReqDTO));
        return documentMap;
    }
}
