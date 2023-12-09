package pm2_5.studypartner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pm2_5.studypartner.dto.document.DocumentDTO;

import pm2_5.studypartner.dto.document.MainScreenDTO;
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

    // 문서 내역 조회
    @GetMapping("/mainPage/{memberId}")
    public MainScreenDTO getDocumentList(@PathVariable Long memberId){

        return documentService.findDocumentList(memberId);
    }

    // 텍스트를 이용하여 문서 생성
    @PostMapping("/text")
    public Map<String, Long> addTransDoc(@ModelAttribute TextTransReqDTO textTransReqDTO) throws JsonProcessingException {

        Map<String, Long> documentMap = new HashMap<>();
        documentMap.put("documentId", documentService.registerTextTransDoc(textTransReqDTO));
        return documentMap;
    }

    // 이미지를 이용하여 문서 생성
    @PostMapping(value = "/img")
    public Map<String, Long> addTransImgDoc(@ModelAttribute ImgTransReqDTO imgTransReqDTO) throws IOException {

        Map<String, Long> documentMap = new HashMap<>();
        String extractedText = documentService.imgOCR(imgTransReqDTO);
        TextTransReqDTO textTransReqDTO = new TextTransReqDTO(imgTransReqDTO.getDocumentTitle(), imgTransReqDTO.getMemberId(), "ko", "en", extractedText);
        documentMap.put("documentId", documentService.registerTextTransDoc(textTransReqDTO));
        return documentMap;
    }

    @PostMapping(value = "/ocr", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String ocrTest(@ModelAttribute ImgTransReqDTO imgTransReqDTO) throws IOException {
        return documentService.imgOCR(imgTransReqDTO);
    }

    // 문서 조회
    @GetMapping("/{documentId}")
    public DocumentDTO getDocument(@PathVariable Long documentId){
        return documentService.findDocument(documentId);
    }

    @DeleteMapping("/{documentId}")
    public void removeDocument(@PathVariable Long documentId){
        documentService.deleteDocument(documentId);
    }
}
