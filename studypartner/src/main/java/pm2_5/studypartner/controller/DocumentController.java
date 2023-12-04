package pm2_5.studypartner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.papago.ImgTransReqDTO;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
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
    public Map<String, Long> addTransDoc(@ModelAttribute TextTransReqDTO textTransReqDTO){

        Map<String, Long> documentMap = new HashMap<>();
        documentMap.put("documentId", documentService.registerTransDoc(textTransReqDTO));
        return documentMap;
    }

    @PostMapping(value = "/imgTrans")
    public Map<String, Long> addTransImgDoc(@ModelAttribute ImgTransReqDTO imgTransReqDTO) throws IOException {

        Map<String, Long> documentMap = new HashMap<>();
        documentMap.put("documentId", documentService.registerImgTransDoc(imgTransReqDTO));
        return documentMap;
    }
}
