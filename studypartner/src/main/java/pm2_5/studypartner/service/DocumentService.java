package pm2_5.studypartner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocTransReqDTO;
import pm2_5.studypartner.dto.document.DocTransRespDTO;
import pm2_5.studypartner.dto.document.TranslatedImageDTO;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DocumentService {
    @Value("${naver.text.client.id}")
    private String clientId;

    @Value("${naver.text.secret.id}")
    private String secretId;

    public String papagoText(DocTransReqDTO docTransReqDTO) {

        String text = removePunctuation(docTransReqDTO.getText());

        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", secretId);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = String.format("source=%s&target=%s&text=%s",
                docTransReqDTO.getSource(), docTransReqDTO.getTarget(), text);

        String apiUrl = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

        WebClient webClient = WebClient.create();

        DocTransRespDTO responseBody = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(DocTransRespDTO.class)
                .block();

        return responseBody.getMessage().getResult().getTranslatedText();
    }

    public static String removePunctuation(String input) {
        // 구두점을 제거하는 정규식 패턴
        String regex = "[\\p{Punct}\n\r]";;

        // 정규식을 사용하여 구두점을 제거
        return input.replaceAll(regex, "");
    }

    public String papagoImg(DocImgTransReqDTO docImgTransReqDTO) throws IOException {

        System.out.println(docImgTransReqDTO);
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", secretId);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String apiUrl = "https://naveropenapi.apigw.ntruss.com/image-to-text/v1/translate";

        WebClient webClient = WebClient.create();

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(docImgTransReqDTO.getImage().getBytes()){
            @Override
            public String getFilename(){
                return docImgTransReqDTO.getImage().getOriginalFilename();
            }
        });

        body.add("source", docImgTransReqDTO.getSource());
        body.add("target", docImgTransReqDTO.getTarget());

        TranslatedImageDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(TranslatedImageDTO.class)
                .block();

        return response.getData().getTargetText();
    }

}
