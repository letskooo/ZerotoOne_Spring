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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocTransReqDTO;
import pm2_5.studypartner.dto.document.DocTransRespDTO;
import pm2_5.studypartner.dto.document.DocImgTransRespDTO;

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

        // 구두점 및 번역에 방해되는 특수문자 제거
        String text = removePunctuation(docTransReqDTO.getText());

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", secretId);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 바디 설정
        String requestBody = String.format("source=%s&target=%s&text=%s",
                docTransReqDTO.getSource(), docTransReqDTO.getTarget(), text);

        // 요청 URL
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

        WebClient webClient = WebClient.create();

        // 요청 및 응답
        DocTransRespDTO responseBody = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(DocTransRespDTO.class)
                .block();

        // 번역된 텍스트 반환
        return responseBody.getMessage().getResult().getTranslatedText();
    }

    public static String removePunctuation(String input) {
        // 구두점을 제거하는 정규식 패턴
        String regex = "[\\p{Punct}\n\r]";;

        // 정규식을 사용하여 구두점을 제거
        return input.replaceAll(regex, "");
    }

    public String papagoImg(DocImgTransReqDTO docImgTransReqDTO) throws IOException {

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", secretId);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 URL
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/image-to-text/v1/translate";

        WebClient webClient = WebClient.create();

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 이미지 파일을 바이트 배열로 변환하여 요청 바디에 추가
        body.add("image", new ByteArrayResource(docImgTransReqDTO.getImage().getBytes()){
            // 파일 이름이 필요할 시 사용
            @Override
            public String getFilename(){
                return docImgTransReqDTO.getImage().getOriginalFilename();
            }
        });

        body.add("source", docImgTransReqDTO.getSource());
        body.add("target", docImgTransReqDTO.getTarget());

        // 요청 및 응답
        DocImgTransRespDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(DocImgTransRespDTO.class)
                .block();

        // 번역된 텍스트 반환
        return response.getData().getTargetText();
    }

}
