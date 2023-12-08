package pm2_5.studypartner.util;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.dto.document.TextRespDTO;
import pm2_5.studypartner.dto.papago.ImgTransReqDTO;
import pm2_5.studypartner.dto.papago.ImgTransRespDTO;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
import pm2_5.studypartner.dto.papago.TextTransRespDTO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PapagoUtil {
    @Value("${naver.text.client.id}")
    private String clientId;

    @Value("${naver.text.secret.id}")
    private String secretId;

    public String translateText(TextTransReqDTO textTransReqDTO) {

        String escapedText = removeEscape(textTransReqDTO.getText());
        // 구두점 및 번역에 방해되는 특수문자 제거

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", secretId);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 바디 설정
        String requestBody = String.format("source=%s&target=%s&text=%s",
                textTransReqDTO.getSource(), textTransReqDTO.getTarget(), textTransReqDTO.getText());

        // 요청 URL
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";

        WebClient webClient = WebClient.create();

        // 요청 및 응답
        TextTransRespDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(TextTransRespDTO.class)
                .block();

        String translated = response.getMessage().getResult().getTranslatedText();

        return translated;
    }

    public TextRespDTO translateImg(ImgTransReqDTO imgTransReqDTO) throws IOException {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID",clientId);
        headers.set("X-NCP-APIGW-API-KEY",secretId);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 요청 URL
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/image-to-text/v1/translate";

        WebClient webClient = WebClient.create();

        BufferedImage originalImage = ImageIO.read(imgTransReqDTO.getImage().getInputStream());
        int newWidth = 1900;
        int newHeight = 900;
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        // 조정된 이미지를 바이트 배열로 변환하여 요청 바디에 추가
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", baos);
        byte[] resizedImageBytes = baos.toByteArray();

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();


        // 이미지 파일을 바이트 배열로 변환하여 요청 바디에 추가
        body.add("image", new ByteArrayResource(resizedImageBytes) {
            @Override
            public String getFilename() {
                return imgTransReqDTO.getImage().getOriginalFilename();
            }
        });

            body.add("source", imgTransReqDTO.getSource());
            body.add("target", imgTransReqDTO.getTarget());

        // 요청 및 응답
        ImgTransRespDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(ImgTransRespDTO.class)
                .block();

        System.out.println(response.getData().getSourceText());

        // 번역된 텍스트 특수문자 제거
        String translated = removeEscape(response.getData().getTargetText());

        return new TextRespDTO(response.getData().getSourceText(), translated);
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
