package pm2_5.studypartner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocTransReqDTO;
import pm2_5.studypartner.service.DocumentService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/translate")
    public String registerDoc(@ModelAttribute DocTransReqDTO docTransReqDTO){

        String translated = documentService.papagoText(docTransReqDTO);

        return translated;
    }

    @PostMapping(value = "/imgTrans")
    public String registerImgDoc(@ModelAttribute DocImgTransReqDTO docImgTransReqDTO) throws IOException {
        return documentService.papagoImg(docImgTransReqDTO);
    }


}
