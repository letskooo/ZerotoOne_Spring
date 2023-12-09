package pm2_5.studypartner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.dto.papago.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NaverCloudUtil {
    @Value("${naver.text.client.id}")
    private String clientId;

    @Value("${naver.text.secret.id}")
    private String secretId;

    @Value("${naver.ocr.secret.id}")
    private String secretKey;

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

    public String extractText(ImgTransReqDTO imgTransReqDTO) throws IOException {

        BufferedImage originalImage = ImageIO.read(imgTransReqDTO.getImage().getInputStream());

        // Create a new ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Write the BufferedImage into the ByteArrayOutputStream as PNG
        ImageIO.write(originalImage, "png", baos);

        byte[] fileContent = baos.toByteArray();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        List<ClovaReqDTO.Image> imageList = new ArrayList<>();
        ClovaReqDTO.Image image = new ClovaReqDTO.Image("png", encodedString, imgTransReqDTO.getDocumentTitle());
        imageList.add(image);
        ClovaReqDTO clovaReqDTO = new ClovaReqDTO("V2", imgTransReqDTO.getMemberId().toString(), System.currentTimeMillis(), "ko", imageList);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";

        jsonString = objectMapper.writeValueAsString(clovaReqDTO);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-OCR-SECRET",secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 URL
        String apiUrl = "https://jb4ae7jsws.apigw.ntruss.com/custom/v1/26820/8f422bf5361c5d844df513e0f54ca2190fedddc9829407b0ee1f46f14fc8ae7c/general";

        WebClient webClient = WebClient.create();

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 요청 및 응답
        ClovaRespDTO response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(jsonString))
                .retrieve()
                .bodyToMono(ClovaRespDTO.class)
                .block();

        List<ClovaRespDTO.Image> images = response.getImages();

        StringBuilder sb = new StringBuilder();
        for(ClovaRespDTO.Image oneImage : images){
            for(ClovaRespDTO.Image.Field field : oneImage.getFields()){
                sb.append(field.getInferText()).append(" ");
                if(field.isLineBreak()){
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
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
