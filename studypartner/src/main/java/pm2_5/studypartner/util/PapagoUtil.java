package pm2_5.studypartner.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.domain.Member;
import pm2_5.studypartner.dto.document.DocImgTransReqDTO;
import pm2_5.studypartner.dto.document.DocImgTransRespDTO;
import pm2_5.studypartner.dto.document.DocTextTransReqDTO;
import pm2_5.studypartner.dto.document.DocTextTransRespDTO;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PapagoUtil {
    @Value("${naver.text.client.id}")
    private String clientId;

    @Value("${naver.text.secret.id}")
    private String secretId;

    public String translateText(DocTextTransReqDTO docTextTransReqDTO) {

        String escapedText = removeEscape(docTextTransReqDTO.getText());
        // 구두점 및 번역에 방해되는 특수문자 제거

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", secretId);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 바디 설정
        String requestBody = String.format("source=%s&target=%s&text=%s",
                docTextTransReqDTO.getSource(), docTextTransReqDTO.getTarget(), escapedText);

        // 요청 URL
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

        WebClient webClient = WebClient.create();

        // 요청 및 응답
        DocTextTransRespDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(DocTextTransRespDTO.class)
                .block();

        String translated = response.getMessage().getResult().getTranslatedText();

        return translated;
    }

    public String translateImg(DocImgTransReqDTO docImgTransReqDTO) throws IOException {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID",clientId);
        headers.set("X-NCP-APIGW-API-KEY",secretId);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 요청 URL
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/image-to-text/v1/translate";

        WebClient webClient = WebClient.create();

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 이미지 파일을 바이트 배열로 변환하여 요청 바디에 추가
            body.add("image",new ByteArrayResource(docImgTransReqDTO.getImage().getBytes())

        {
            // 파일 이름이 필요할 시 사용
            @Override
            public String getFilename () {
            return docImgTransReqDTO.getImage().getOriginalFilename();
        }
        });

            body.add("source",docImgTransReqDTO.getSource());
            body.add("target",docImgTransReqDTO.getTarget());

        // 요청 및 응답
        DocImgTransRespDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(DocImgTransRespDTO.class)
                .block();


        // 번역된 텍스트 특수문자 제거
        String translated = removeEscape(response.getData().getTargetText());

        return translated;
    }

    public static String removeEscape(String input) {
        // 정규식 패턴: 알파벳, 숫자, 공백, 일부 특수 문자만 허용
        String regexPattern = "[^a-zA-Z0-9가-힣\\s'\".,?!]";

        // 패턴에 맞는 문자열을 찾기 위한 Matcher 생성
        Matcher matcher = Pattern.compile(regexPattern).matcher(input);

        // 찾은 문자열을 제거하고 결과 출력
        String result = matcher.replaceAll("");
        return result;
    }
}
