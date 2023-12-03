package pm2_5.studypartner.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pm2_5.studypartner.domain.Keyword;
import pm2_5.studypartner.dto.keyword.KeywordsDTO;
import pm2_5.studypartner.dto.keyword.OpenaiRespDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OpenaiUtil {

    @Value("${openai.my_key}")
    private String chatkey;

    public String extractContent(String translatedText) throws JsonProcessingException {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + chatkey);

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> message1 = new HashMap<>();
        message1.put("role", "system");
        message1.put("content", """
                You need to extract important keywords and descriptions of those keywords based on the material I've given you.
                I'll give you the steps, and you'll follow them to extract the keywords and descriptions.
                1. Go through the material I've given you and extract the keywords you think are important.
                2. generate the descriptions for those keywords from the source material.
                3. count the number of keywords and let me know key count
                4. pair those keywords with their descriptions and get back to me only a JSON style response that jackson can parse. I'll give you an example response format in ```.
                
                ```{"count" : 2, "keywords" : [{"keyword":"key", "description":"desc"},{"keyword":"key", "description":"desc"}]}```
                """) ;
        Map<String, Object> message2 = new HashMap<>();
        message2.put("role", "user");
        message2.put("content", translatedText);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4");
        body.put("messages", Arrays.asList(message1, message2));
        body.put("temperature", 0);

        WebClient webClient = WebClient.create();

        // 요청 및 응답
        String response = webClient.post()
                .uri(apiUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();

        OpenaiRespDTO openaiRespDTO = objectMapper.readValue(response, OpenaiRespDTO.class);

        StringBuilder sb = new StringBuilder();
        for(OpenaiRespDTO.Choice choice : openaiRespDTO.getChoices()) {
            String keywords = choice.getMessage().getContent();
            String json = extractJson(keywords);
            System.out.println(json);

            sb.append(json);
        }
        return sb.toString();
    }

    public static String extractJson(String input) {
        String pattern = "\\{.*\\}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);

        if (m.find()) {
            return m.group(0);
        } else {
            return null; // JSON 부분이 없을 경우 처리 방법을 결정
        }
    }
}
