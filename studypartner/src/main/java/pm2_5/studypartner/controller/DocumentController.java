package pm2_5.studypartner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocTextTransReqDTO;
import pm2_5.studypartner.service.DocumentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/textTrans")
    public Document addTransDoc(@ModelAttribute DocTextTransReqDTO docTextTransReqDTO){

        return documentService.registerTransDoc(docTextTransReqDTO);
    }

    @PostMapping(value = "/imgTrans")
    public Document addTransImgDoc(@ModelAttribute DocImgTransReqDTO docImgTransReqDTO) throws IOException {

        return documentService.registerImgTransDoc(docImgTransReqDTO);
    }


}
