package pm2_5.studypartner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pm2_5.studypartner.domain.Context;
import pm2_5.studypartner.domain.Document;
import pm2_5.studypartner.dto.context.ContextsDTO;
import pm2_5.studypartner.dto.papago.TextTransReqDTO;
import pm2_5.studypartner.repository.ContextRepository;
import pm2_5.studypartner.repository.DocumentRepository;
import pm2_5.studypartner.util.OpenaiUtil;
import pm2_5.studypartner.util.NaverCloudUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContextService {

    public final NaverCloudUtil naverCloudUtil;
    public final OpenaiUtil openaiUtil;
    public final ContextRepository contextRepository;
    public final DocumentRepository documentRepository;

    public ContextsDTO registerContext(Long documentId) throws JsonProcessingException {
        
        // 해당 자료를 가져옴
        Document findDocument = documentRepository.findById(documentId).get();
        String translatedText = findDocument.getEnContent();
        
        // 역할 설정
        String system = """
                I need to provide a summary of the paragraphs from the data I provided.
                I'll tell you the steps, so follow them.
                1. Look at the data and divide the paragraphs that change the flow of the text. This will have key content in JSON sentences.
                2. Create a summary of each paragraph. This will have a key summary in JSON sentence
                3. Count the number of paragraphs and let me know the number of paragraphs
                4. Please write JSON-style responses so that Jackson can paraphrase them by linking them with the summary. I'll show you an example of a response format in ```.
                ```
                {
                    "count": 2,
                    "contexts": [
                        {
                            "content": "Korean eGovernment Standard Framework relies heavily on Spring for its base technology, making it recommended by the National Information Society Agency for web services provided by public institutions. Essentially, this framework standardizes and mediates necessary components. This is why the term ‘advice’ is frequently used in its context. Despite its complexity due to the application of design patterns and architectures, one can understand it better by working on practical examples. A range of Integrated Development Environments (IDEs) like Spring Tools and JetBrains' Intellij IDEA offer excellent Spring framework support.",
                            "summary": "The Korean eGovernment Standard Framework, recommended for public institution web services, relies on Spring technology. Its complexity is best understood through practical application and is supported by various Integrated Development Environments(IDEs)."
                        },
                        {
                            "content": "The characteristics of the spring framework include the 'Plain Old Java Object (POJO)' method, in which objects aren't subordinate to the platform as opposed to Java EE's EJB. This makes support for existing libraries easier, as it does not require direct implementation of a particular interface. The framework also features 'Aspect-Oriented Programming (AOP)', whereby functions used by various modules can be independently managed regardless of the hierarchy or interface structure.",
                            "summary": "Key features of the Spring Framework include the POJO method for light object management, and AOP that allows for an independent management of commonly used functions in module structures."
                        }
                    ]
                }
                ```
                """;

        // chat gpt의 응답을 추출
        String json = openaiUtil.extractContent(system, translatedText, false);
        // chat gpt의 응답을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        ContextsDTO contextsDTO = objectMapper.readValue(json, ContextsDTO.class);

        ContextsDTO newContexts = new ContextsDTO(contextsDTO.getCount());

        // 각 keyword를 확인 및 저장
        for(ContextsDTO.ContextDTO context : contextsDTO.getContexts()) {
            // 키워드와 설명 번역
            TextTransReqDTO textTransReqDTO = new TextTransReqDTO(documentId, "en", "ko", context.getContent());
            String translatedContent = naverCloudUtil.translateText(textTransReqDTO);
            textTransReqDTO = new TextTransReqDTO(documentId, "en", "ko", context.getSummary());
            String translatedSummary = naverCloudUtil.translateText(textTransReqDTO);

            // 키워드 저장
            Context newContext = new Context(findDocument, translatedContent, translatedSummary);
            contextRepository.save(newContext);

            newContexts.getContexts().add(new ContextsDTO.ContextDTO(newContext.getContent(), newContext.getSummary()));
        }
        newContexts.setDocumentId(documentId);

        return newContexts;
    }

    // 문단 자료 조회
    public ContextsDTO findContexts(Long documentId){

        List<Context> contexts = contextRepository.findContextsByDocumentId(documentId);

        ContextsDTO contextsDTO = new ContextsDTO();
        List<ContextsDTO.ContextDTO> contextDTOList = new ArrayList<>();
        int count = 0;
        for(Context context : contexts){
            contextDTOList.add(new ContextsDTO.ContextDTO(context.getContent(), context.getSummary()));
            count += 1;
        }

        contextsDTO.setContexts(contextDTOList);
        contextsDTO.setCount(count);
        contextsDTO.setDocumentId(documentId);

        return contextsDTO;
    }

    // 키워드 자료 삭제
    public void deleteContexts (Long documentId){
        contextRepository.deleteContextsByDocumentId(documentId);
    }
}
